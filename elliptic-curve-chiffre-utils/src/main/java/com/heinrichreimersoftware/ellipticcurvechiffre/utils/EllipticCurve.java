package com.heinrichreimersoftware.ellipticcurvechiffre.utils;

public class EllipticCurve{
    private long a;
    private long b;
    private long p;

    public EllipticCurve(long a, long b, long p){
        this.a = a;
        this.b = b;
        this.p = p;
    }

    public long getA(){
        return a;
    }

    public long getB(){
        return b;
    }
    
    public long getP(){
        return p;
    }

    public Point addPoints(Point p, Point q) throws PointOfInfinityException{
        if(p.equals(q)){
            return doublePoint(p);
        }
        if(p.getX() == q.getX()){
            throw new PointOfInfinityException();
        }
        
        long slope = p.getSlope(q, this);
        long x = ((long)(Math.pow(slope, 2)) - p.getX() - q.getX())%getP();
        long y = (slope * (p.getX() - x) - p.getY())%getP();
        return new Point(x, y, getP());
    }

    public Point doublePoint(Point p) throws PointOfInfinityException{
        if(p.getY() == 0){
            throw new PointOfInfinityException();
        }
        long slope = p.getSlope(this);
        long x = ((long)(Math.pow(slope, 2)) - 2 * p.getX())%getP();
        long y = (slope * (p.getX() - x) - p.getY())%getP();
        return new Point(x, y, getP());
    }

    public Point multiplyPoint(Point p, long k) throws PointOfInfinityException{
        if(k <= 0){
            throw new IllegalArgumentException("Cannot multiply " + k + " times.");
        }
        else if(k == 1){
            return p;
        }
        else if(k == 2){
            return doublePoint(p);
        }
        else{
            return addPoints(doublePoint(p), multiplyPoint(p, k-2));
        }
    }

    public long orderOf(Point p){
        long i = 0;
        Point q = p;
        Point temp;
        while(true){
            try{
                temp = addPoints(p, q);   
                q=new Point(temp.getX(), temp.getY(), getP());
                //System.out.println("q_"+i+"=("+q.getX()+"|"+q.getY()+")");
                i++;
            } catch(PointOfInfinityException ignore){
                break;
            }
        }
        return i+2;
    }
    
}
