package com.heinrichreimersoftware.ellipticcurvechiffre.utils;

public class Point{
    private long x;
    private long y;

    public Point(long x, long y, long p){
        if(x<0){
            x+=p;
        }
        this.x = x;
        if(y<0){
            y+=p;
        }
        this.y = y;
    }

    public long getX(){
        return x;
    }

    public long getY(){
        return y;
    }

    public long getSlope(Point p, EllipticCurve c){
        return (((p.getY() - y)%c.getP()) * getInversiveModular((p.getX() - x),c))%c.getP();
    }

    public long getSlope(EllipticCurve c){
        if(!isOnEllipticCurve(c)){
            throw new IllegalArgumentException("Point must be on elliptic curve c.");
        }
        return (((3 * (long)(Math.pow(x, 2)) + c.getA())%c.getP()) * (getInversiveModular(2 * y,c)%c.getP()))%c.getP();
    }

    public long getInversiveModular(long b, EllipticCurve c){
        if(b==0){
            return 0;
        }
        long n=1;
        while((1+c.getP()*n)%b!=0){
            n++;
        }
        return (1+c.getP()*n)/b;
    }

    public boolean isOnEllipticCurve(EllipticCurve c){
        return Math.pow(y, 2)%c.getP() == (Math.pow(x, 3) + c.getA() * x + c.getB())%c.getP();
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Point))
            return false;
        Point p = (Point) o;
        return x == p.getX() && y == p.getY();
    }
}
