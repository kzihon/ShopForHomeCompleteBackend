package com.api.server.coupon;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CouponService {

  private final CouponRepository couponRepository;

  public CouponService(CouponRepository couponRepository) {
    this.couponRepository = couponRepository;
  }

  @PreAuthorize("hasAuthority('admin:read')")
  public List<Coupon> getAllCoupons() {
    return couponRepository.findAll();
  }

  public Optional<Coupon> getCouponById(Long id) {
    return couponRepository.findById(id);
  }

  public Coupon verifyCouponByCode(String code) {
    Optional<Coupon>  coupon = couponRepository.findByCode(code);
    if(coupon.isPresent()){
      if(coupon.get().isActive()){
        return couponRepository.findByCode(code).get();
      }

    }


    return null;
  }

  @PreAuthorize("hasAuthority('admin:create')")
  public Coupon createCoupon(Coupon coupon) {
    return couponRepository.save(coupon);
  }

  @PreAuthorize("hasAuthority('admin:update')")
  public Coupon updateCoupon(Long id, Coupon updatedCoupon) {
    Coupon coupon = couponRepository.findById(id).orElseThrow(()->new RuntimeException("Coupon not found"));

    coupon.setCouponName(updatedCoupon.getCouponName());
    coupon.setCode(updatedCoupon.getCode());
    coupon.setDiscount(updatedCoupon.getDiscount());
    coupon.setActive(updatedCoupon.isActive());

    return coupon;



  }

  @PreAuthorize("hasAuthority('admin:delete')")
  public void deleteCoupon(Long id) {
    couponRepository.deleteById(id);
  }
}