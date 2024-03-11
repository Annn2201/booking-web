package com.example.webbookingroom.service.impl;

import com.example.webbookingroom.config.JwtUtilities;
import com.example.webbookingroom.dto.VoucherDTO;
import com.example.webbookingroom.exception.CustomException;
import com.example.webbookingroom.model.User;
import com.example.webbookingroom.model.UserVoucher;
import com.example.webbookingroom.model.Voucher;
import com.example.webbookingroom.repository.UserRepository;
import com.example.webbookingroom.repository.UserVoucherRepository;
import com.example.webbookingroom.repository.VoucherRepository;
import com.example.webbookingroom.service.VoucherService;
import com.example.webbookingroom.util.PageUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.Predicate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {
    private static final int LIMIT = 3;
    private static final String SORT = "expiryDate-DESC";
    private final JwtUtilities jwtUtilities;
    private final VoucherRepository voucherRepository;
    private final UserVoucherRepository userVoucherRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<?> findVouchers(int currentPage, String hotelName, HttpServletRequest request) {
        Specification<Voucher> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isEmpty(hotelName)) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + hotelName + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<Voucher> vouchers = voucherRepository.findAll(spec, PageUtil.createPageable(currentPage, LIMIT, SORT));
        String username = jwtUtilities.getUsername(request);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new CustomException("User not found"));
        List<UserVoucher> userVouchers = userVoucherRepository.findByUserId(user.getUserId());
        List<Long> userVoucherIds = getUserVoucherIds(userVouchers);
        List<Voucher> result = getVouchersAvailable(vouchers, userVoucherIds);
        if (result.isEmpty()) {
            return ResponseEntity.ok("Không có ưu đãi nào khả dụng");
        }
        List<VoucherDTO> resultDTO = mapResultToDTO(userVouchers, result);
        return ResponseEntity.ok(resultDTO);
    }

    @NotNull
    private static List<Voucher> getVouchersAvailable(Page<Voucher> vouchers, List<Long> userVoucherIds) {
        return vouchers.getContent().stream()
                .filter(voucher -> userVoucherIds.contains(voucher.getId()))
                .toList();
    }

    @NotNull
    private static List<Long> getUserVoucherIds(List<UserVoucher> userVouchers) {
        return userVouchers.stream()
                .map(UserVoucher::getVoucherId)
                .distinct().toList();
    }

    @NotNull
    private static List<VoucherDTO> mapResultToDTO(List<UserVoucher> userVouchers, List<Voucher> result) {
        return result.stream()
                .map(voucher -> {
                    VoucherDTO voucherDTO = new VoucherDTO();
                    BeanUtils.copyProperties(voucher, voucherDTO);
                    voucherDTO.setExpiryDate(Objects.requireNonNull(userVouchers.stream()
                            .filter(userVoucher -> voucher.getId().equals(userVoucher.getVoucherId()))
                            .map(UserVoucher::getExpiryDate)
                            .findFirst().orElse(null)).toString());
                    return voucherDTO;
                }).toList();
    }
}
