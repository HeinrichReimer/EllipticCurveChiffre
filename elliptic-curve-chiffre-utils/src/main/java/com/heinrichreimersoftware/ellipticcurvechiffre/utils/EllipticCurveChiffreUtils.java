package com.heinrichreimersoftware.ellipticcurvechiffre.utils;

public class EllipticCurveChiffreUtils{
    private EllipticCurve curve;
    private Point g;
    private long n;

    public EllipticCurveChiffreUtils(EllipticCurve curve, Point g){
        if(!g.isOnEllipticCurve(curve)){
            throw new IllegalArgumentException("Point g must be on elliptic curve.");
        }
        
        this.curve = curve;
        this.g = g;
        this.n = curve.orderOf(g);
    }

    public EllipticCurve getCurve() {
        return curve;
    }

    public Point getG() {
        return g;
    }

    public long getOrderOfG(){
        return curve.orderOf(g);
    }

    public Point calculatePublicKey(long beta){
        //Return: public key B
        if(beta >= n){
            throw new IllegalArgumentException("Beta must be smaller than n.");
        }
        try{
            return curve.multiplyPoint(g, beta);
        } catch(PointOfInfinityException ignore){
            throw new IllegalArgumentException("Beta must be smaller than n!");
        }
    }

    public Point calculateSharedPrivateKey(Point a, long beta){
        //Return: shared private key P
        if(beta >= n){
            throw new IllegalArgumentException("Beta must be smaller than n.");
        }
        try{
            return curve.multiplyPoint(a, beta);
        } catch(PointOfInfinityException ignore){
            throw new IllegalArgumentException("Beta must be smaller than n!");
        }
    }
}
