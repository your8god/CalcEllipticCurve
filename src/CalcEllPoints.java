import java.math.BigInteger;
import java.util.ArrayList;

public class CalcEllPoints
{
    static ArrayList<Pair> points = new ArrayList<>();

    public static void main(String[] args)
    {
        BigInteger A = BigInteger.valueOf(3);
        BigInteger B = BigInteger.valueOf(8);
        BigInteger p = BigInteger.valueOf(11);

        pointsEllCurve(A, B);

        for (int i = 1; i < points.size(); i++)
        {
            Pair kP = mult(points.get(i), BigInteger.valueOf(points.size()), A, p);
            System.out.println(kP.x + " " + kP.y);
        }
        /*Scanner scan = new Scanner(System.in);

        Pair P = new Pair();
        String[] xy = scan.nextLine().split(" ");
        P.x = new BigInteger(xy[0]);
        P.y = new BigInteger(xy[1]);
        BigInteger n = scan.nextBigInteger();

        Pair kP = ProofZeroCurve.mult(P, n, A, p);
        System.out.println(kP.x + " " + kP.y);*/
    }

    static void pointsEllCurve(BigInteger A, BigInteger B)
    {
        int a = A.intValue(), b = B.intValue();
        points.add(new Pair());

        for (int i = 0; i < 11; i++)
        {
            int x = (i * i * i + a * i + b) % 11;

            for (int j = 0; j < 11; j++)
                if ((j * j) % 11 == x)
                    points.add(new Pair(BigInteger.valueOf(i), BigInteger.valueOf(j)));
        }

        System.out.println(points.size());
        for (int i = 0; i < points.size(); i++)
            System.out.println(points.get(i).x + " " + points.get(i).y);
        System.out.println("");
    }

    public static Pair sum(Pair x1y1, Pair x2y2, BigInteger p, BigInteger A) {
        try {
            if (x1y1 == null) {
                return null;
            }

            BigInteger x1 = x1y1.x, y1 = x1y1.y, x2 = x2y2.x, y2 = x2y2.y, alph;

            if (x1.equals(x2) && y1.equals(y2)) {
                if (y1.equals(BigInteger.ZERO))
                    return null;
                else
                    alph = ((x1.multiply(x1).multiply(BigInteger.valueOf(3)).add(A)).multiply((BigInteger.TWO.multiply(y1)).modInverse(p))).mod(p);
            } else
                alph = ((y2.subtract(y1)).multiply((x2.subtract(x1)).modInverse(p))).mod(p);

            BigInteger x3 = (alph.multiply(alph).subtract(x1).subtract(x2)).mod(p),
                    y3 = ((x1.subtract(x3)).multiply(alph).subtract(y1)).mod(p);

            return new Pair(x3, y3);
        } catch (ArithmeticException e) {
            return null;
        }
    }

    public static Pair mult(Pair point, BigInteger n, BigInteger A, BigInteger p)
    {
        Pair res = point;
        for (int i = 2; i <= n.intValue(); i++) {
            res = sum(res, point, p, A);
            if (res == null)
            {
                return new Pair(BigInteger.valueOf(-1), BigInteger.valueOf(i));
            }
        }
        return res;
    }
}
