package com.api.server.coupon;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/v1/coupons")
@RequiredArgsConstructor
public class CouponController {

  private final CouponService couponService;

  @GetMapping
  public List<Coupon> getAllCoupons() {
    return couponService.getAllCoupons();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Coupon> getCouponById(@PathVariable Long id) {
    return couponService.getCouponById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/verify/{code}")
  public ResponseEntity<Coupon> verifyCouponByCode(@PathVariable String code) {

    Coupon coupon = couponService.verifyCouponByCode(code);
    return ResponseEntity.status(HttpStatus.OK).body(coupon);
  }

  @PostMapping
  public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon) {
    return new ResponseEntity<>(couponService.createCoupon(coupon), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Coupon> updateCoupon(@PathVariable Long id, @RequestBody Coupon updatedCoupon) {
    Coupon result = couponService.updateCoupon(id, updatedCoupon);
    return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
    couponService.deleteCoupon(id);
    return ResponseEntity.noContent().build();
  }
}