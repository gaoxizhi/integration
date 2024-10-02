package net.gaox.math;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.security.spec.ECFieldFp;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;

/**
 * <p> secp256k1 椭圆曲线 </p>
 * Bitcoin, Ethereum etc多樣密碼貨幣都用secp256k1這條橢圓曲線。
 * 本程式實做了secp256k1這條橢圓曲線上的「純量乘法」(未優化)。要明白
 * ECC的困難在於，已知兩點Q, P且某未知正整數n使得Q=[n]P，求n=?
 * 此稱為EC Discrete Log難題。
 *
 * @author gaox·Eric
 * @date 2025/11/22 22:37
 */
public class Secp256k1 {
    // 橢圓曲線E: y^2=x^3+a*x+b (mod p)
    static EllipticCurve E;
    static BigInteger a = BigInteger.ZERO;
    static BigInteger b = BigInteger.valueOf(7);
    static ECFieldFp Fp;
    // p=2**256 - 2**32 - 977
    static BigInteger p = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFC2F", 16);

    final static BigInteger ZERO = BigInteger.ZERO;
    final static BigInteger ONE = BigInteger.ONE;
    final static BigInteger TWO = BigInteger.valueOf(2);
    final static BigInteger THREE = BigInteger.valueOf(3);
    final static BigInteger FOUR = BigInteger.valueOf(4);
    final static BigInteger INT_27 = BigInteger.valueOf(27);
    final static ECPoint Inf = ECPoint.POINT_INFINITY;

    static BigInteger x(ECPoint P) {
        return P.getAffineX();
    }

    static BigInteger y(ECPoint P) {
        return P.getAffineY();
    }

    /**
     * 两点的加法
     *
     * @param p p
     * @param q q
     * @return p+q 的点
     */
    public static ECPoint add(ECPoint p, ECPoint q) {
        // 穿越P,Q直線斜率
        BigInteger m;
        if (p.equals(Inf)) {
            return q;
        } else if (q.equals(Inf)) {
            return p;
        } else if (p.equals(q) && y(p).equals(ZERO)) {
            return Inf;
        } else if (x(p).equals(x(q)) && !y(p).equals(y(q))) {
            return Inf;
        } else if (p.equals(q)) {
            // m=(3*x(p)^2+a)*(2*y(p))^(-1)%p
            m = THREE.multiply(x(p).modPow(TWO, Secp256k1.p)).add(a).
                    multiply(TWO.multiply(y(p)).modInverse(Secp256k1.p)).mod(Secp256k1.p);
        } else {
            // m=(y(q)-y(p))*(x(q)-x(p))^(-1)%p
            m = y(q).subtract(y(p)).multiply(x(q).subtract(x(p)).modInverse(Secp256k1.p)).mod(Secp256k1.p);
        }

        // x(R)=m^2-x(p)-x(q)%p
        BigInteger xR = m.modPow(TWO, Secp256k1.p).subtract(x(p)).subtract(x(q)).mod(Secp256k1.p);

        // y(R)=m*(x(p)-x(R))-y(p)%p
        BigInteger yR = m.multiply(x(p).subtract(xR)).subtract(y(p)).mod(Secp256k1.p);
        return new ECPoint(xR, yR);
    }

    public static ECPoint minus(ECPoint p) {
        //[-1]p
        BigInteger yP = y(p).negate().mod(Secp256k1.p);
        return new ECPoint(x(p), yP);
    }

    public static ECPoint multiply(BigInteger n, ECPoint P) {
        // 使用LSBF二元法算[n]P
            // n<0
        if (n.signum() < 0) {
            n = n.negate();
            P = minus(P);
            //[n]P=[-n](-P)
        }
        ECPoint R = Inf;
        while (!n.equals(ZERO)) {
                // n%2==1
            if (n.testBit(0)) {
                R = add(R, P);
            }
            // n=n>>1
            n = n.shiftRight(1);
            P = add(P, P);
        }
        return R;
    }

    public static void main(String[] agv) throws Exception {
        SecureRandom rnd = new SecureRandom();
        System.out.println(p.bitLength() + "-bit質數p=\n" + p);
        Fp = new ECFieldFp(p);

        // base point G的座標
        BigInteger xP = new BigInteger("79BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798", 16);
        BigInteger yP = new BigInteger("483ADA7726A3C4655DA4FBFC0E1108A8FD17B448A68554199C47D08FFB10D4B8", 16);
        ECPoint P = new ECPoint(xP, yP);

        E = new EllipticCurve(Fp, a, b);
        System.out.println("橢圓曲線E/Fp:");
        System.out.println("a=" + a);
        System.out.println("b=" + b);

        System.out.println("點G(=P)座標:");
        System.out.println("xP=" + xP);
        System.out.println("yP=" + yP);

        BigInteger n;

        ECPoint Q, Q2;
        long start, end, t0, t1;
        double ratio;

        for (int i = 0; i < 5; i++) {
            n = new BigInteger(254, rnd);
            System.out.println("=====================================================");
            System.out.println("n=" + n);
            start = System.nanoTime();
            Q = multiply(n, P);
            end = System.nanoTime();

            System.out.println("Q=[n]P座標:");
            if (Q.equals(Inf)) {
                System.out.println("Inf");
            }
            System.out.println("xQ=" + x(Q));
            System.out.println("yQ=" + y(Q));
            t0 = end - start;

            // System.out.println(Q.equals(Q2));
            System.out.printf("multiply()計算時間:   %12d nanosec.\n", t0);
        }
    }
}
