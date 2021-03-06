import java.io.*;
import java.util.*;
class Algo {

    static long fact[];
    static long invFact[];
    static long inv[];
    static int NAX = ((int)(2e6 + 100));

    public static long add(long a, long b, long p) {
        return (a % p + b % p + p) % p;
    }
    public static long sub(long a, long b, long p) {
        return (p + a % p - b % p) % p;
    }
    public static long mul(long a, long b, long p) {
        return (a % p * b % p) % p;
    }
    public static long gcd(long a, long b) {
        for(long rem; b > 0; rem = a % b, a = b, b = rem);
        return a;
    }
    public static long lcm(long a, long b) {
        return (a * b) / gcd(a, b);
    }
    public static int log2(long n) {
        int c = 0;
        for(; n > 0; n >>= 1, c++);
        return (c - 1);
    }
    public static long power(long x, long y, long p) {
        long result = 1;
        for(result = 1; y > 0; x = (x % p * x % p) % p, y >>= 1)
            if((y&1) == 1)
                result = (result % p * x % p) % p;
        return result;
    }
    public static long inverse(long a, long p) {
        return power(a, p - 2, p);
    }

    public static void matmul(long a[][], long b[][], long res[][], long p) {
        int M = a.length;
        int N = a[0].length;
        int P = b[0].length;
        long result[][] = new long[M][P];
        for(int i = 0; i < M; i++) {
            for(int j = 0; j < P; j++) {
                result[i][j] = 0;
                for(int k = 0; k < N; k++) {
                    result[i][j] = add(result[i][j], mul(a[i][k], b[k][j], p), p);
                }
            }
        }
        for(int i = 0; i < M; i++)
            for(int j = 0;j < P; j++)
                res[i][j] = result[i][j];
    }
    public static long[][] power(long a[][], long y, long p) {
        long result[][] = new long[a.length][a.length];
        for(int i = 0; i < a.length; i++)
            result[i][i] = 1;
        for(; y > 0; y >>= 1, matmul(a, a, a, p)) {
            if((y&1) == 1)
                matmul(a, result, result, p);
        }
        return result;
    }
    public static long nthFibonacci(long n, long p) {
        long base[][] = {{1, 1}, {1, 0}};
        return power(base, n, p)[0][1];
    }
    public static long[] prefixSum(int a[]) {
        int n = a.length;
        long sum[] = new long[n];
        sum[0] = a[0];
        for(int i = 1; i < n; i++)
            sum[i] = sum[i - 1] + a[i];
        return sum;
    }
    public static long[] prefixSum(long a[]) {
        int n = a.length;
        long sum[] = new long[n];
        sum[0] = a[0];
        for(int i = 1; i < n; i++)
            sum[i] = sum[i - 1] + a[i];
        return sum;
    }
    public static long[] suffixSum(int a[]) {
        int n = a.length;
        long sum[] = new long[n];
        sum[n - 1] = a[n - 1];
        for(int i = n - 2; i >= 0; i--)
            sum[i] = sum[i + 1] + a[i];
        return sum;
    }
    public static long[] suffixSum(long a[]) {
        int n = a.length;
        long sum[] = new long[n];
        sum[n - 1] = a[n - 1];
        for(int i = n - 2; i >= 0; i--)
            sum[i] = sum[i + 1] + a[i];
        return sum;
    }
    public static long nCr_mod_p(int n, int r, long p) {
        return mul(fact[n], mul(invFact[r], invFact[n - r], p), p);
    }
    public static long nthCatalan(int n, long p) {
        return mul(nCr_mod_p(2 * n, n, p), inv[n + 1], p);
    }
    public static void prepare(int nax, long p) {
        NAX = nax;
        computeFactorials(p);
        computeInverseFactorials(p);
        computeInverses(p);
    }
    public static void computeFactorials(long p) {
        fact = new long[NAX];
        fact[0] = 1;
        for(int i = 1; i < NAX; i++)
            fact[i] = mul(fact[i-1], i, p);
    }
    public static void computeInverseFactorials(long p) {
        invFact = new long[NAX];
        invFact[NAX - 1] = power(fact[NAX - 1], p - 2, p);
        for(int i = NAX - 2; i >= 0; i--) {
            invFact[i] = ((i + 1) * invFact[i + 1]) % p;
        }
    }
    public static void computeInverses(long p) {
        inv = new long[NAX];
        inv[0] = 0;
        for(int i = 1; i < NAX; i++) {
            inv[i] = (fact[i - 1] * invFact[i]) % p;
        }
    }
    public static int binarySearch(int a[], int key) {
        int lb = 0;
        int ub = a.length - 1;
        while(lb <= ub) {
            int mid = (lb + ub)/2;
            if(a[mid] == key)
                return mid;
            else if(a[mid] < key)
                lb = mid + 1;
            else
                ub = mid - 1;
        }
        return -1;
    }
    public static int binarySearch(long a[], long key) {
        int lb = 0;
        int ub = a.length - 1;
        while(lb <= ub) {
            int mid = (lb + ub)/2;
            if(a[mid] == key)
                return mid;
            else if(a[mid] < key)
                lb = mid + 1;
            else
                ub = mid - 1;
        }
        return -1;
    }
    public static int numberOfDigits(long a) {
        return Long.toString(a).length();
    }
    public static int sumOfDigits(long a) {
        int c = 0;
        for(; a > 0; c += a % 10, a /= 10);
        return c;
    }
    public static int setBitCount(long a) {
        int c = 0;
        for(; a > 0; c += a % 2, a /= 2);
        return c;
    }
    public static int bisect_left(int a[], int key) {
        int ind = binarySearch(a, key);
        double k = key;
        if(ind != -1) {
            k = ((double)(key)) - 0.5;
        }
        int lb = 0, ub = a.length - 1;
        while(lb <= ub) {
            int mid = (lb + ub) / 2;
            if(a[mid] < k)
                lb = mid + 1;
            else
                ub = mid - 1;
        }
        return lb;
    }
    public static int bisect_left(long a[], long key) {
        int ind = binarySearch(a, key);
        double k = key;
        if(ind != -1) {
            k = ((double)(key)) - 0.5;
        }
        int lb = 0, ub = a.length - 1;
        while(lb <= ub) {
            int mid = (lb + ub) / 2;
            if(a[mid] < k)
                lb = mid + 1;
            else
                ub = mid - 1;
        }
        return lb;
    }
    public static int bisect_right(int a[], int key) {
        int ind = binarySearch(a, key);
        double k = key;
        if(ind != -1) {
            k = ((double)(key)) + 0.5;
        }
        int lb = 0, ub = a.length - 1;
        while(lb <= ub) {
            int mid = (lb + ub) / 2;
            if(a[mid] < k)
                lb = mid + 1;
            else
                ub = mid - 1;
        }
        return lb;
    }
    public static int bisect_right(long a[], long key) {
        int ind = binarySearch(a, key);
        double k = key;
        if(ind != -1) {
            k = ((double)(key)) + 0.5;
        }
        int lb = 0, ub = a.length - 1;
        while(lb <= ub) {
            int mid = (lb + ub) / 2;
            if(a[mid] < k)
                lb = mid + 1;
            else
                ub = mid - 1;
        }
        return lb;
    }
    public static long sum(int a[]) {
        long s = 0;
        for(int i = 0; i < a.length; i++)
            s = s + a[i];
        return s;
    }
    public static long sum(long a[]) {
        long s = 0;
        for(int i = 0; i < a.length; i++)
            s = s + a[i];
        return s;
    }
    private static long merge(int a[], int lb, int ub) {
        int temp[] = new int[ub + 1 - lb];
        long ans = 0;
        int i = lb;
        int mid = (lb + ub)/2;
        int j = (mid + 1);
        int ind = 0;
        while(i <= mid && j <= ub) {
            if(a[i] <= a[j]) {
                temp[ind++] = a[i++];
            }
            else {
                ans = ans + mid + 1 - i;
                temp[ind++] = a[j++];
            }
        }
        while(i <= mid) {
            temp[ind++] = a[i++];
        }
        while(j <= ub) {
            temp[ind++] = a[j++];
        }
        for(i = lb, ind = 0; i <= ub; i++, ind++)
            a[i] = temp[ind];
        return ans;
    }
    private static long mergeSort(int a[], int lb, int ub) {
        long ans = 0;
        if(lb < ub) {
            int mid = (lb + ub)/2;
            ans += mergeSort(a, lb, mid);
            ans += mergeSort(a, mid + 1, ub);
            ans += merge(a, lb, ub);
        }
        return ans;
    }
    public static long countInversions(int a[]) {
        return mergeSort(a, 0, a.length - 1);
    }
    public static int lcs(int a[], int b[]) {
        int m = a.length;
        int n = b.length;
        int dp[][] = new int[m+1][n+1];
        for(int i = 0; i <= m; i++) {
            for(int j = 0; j <= n; j++) {
                if(i == 0 || j == 0) {
                    dp[i][j] = 0;
                }
                else if(a[i-1] == b[j-1]) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                }
                else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        return dp[m][n];
    }
    public static int lcs(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int dp[][] = new int[m+1][n+1];
        for(int i = 0; i <= m; i++) {
            for(int j = 0; j <= n; j++) {
                if(i == 0 || j == 0) {
                    dp[i][j] = 0;
                }
                else if(s1.charAt(i-1) == s2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                }
                else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        return dp[m][n];
    }
    public static void matmul(Fraction f1[][], Fraction f2[][], Fraction res[][]) {
        int m = f1.length;
        int n = f1[0].length;
        int p = f2[0].length;
        Fraction result[][] = new Fraction[m][p];
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < p; j++) {
                result[i][j] = new Fraction(0);
                for(int k = 0; k < n; k++) {
                    result[i][j] = Fraction.add(result[i][j], Fraction.mul(f1[i][k], f2[k][j]));
                }
            }
        }
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < p; j++) {
                res[i][j] = result[i][j];
            }
        }
    }
    public static Fraction[][] power(Fraction f[][], long y) {
        int n = f.length;
        Fraction res[][] = new Fraction[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                res[i][j] = new Fraction(0);
            }
            res[i][i] = new Fraction(1);
        }
        while(y > 0) {
            if((y&1) == 1) {
                matmul(f, res, res);
            }
            matmul(f, f, f);
            y >>= 1;
        }
        return res;
    }
    public static int[] nextGreaterInLeft(int a[], int n) {
        int left_index[] = new int[n];
        Arrays.fill(left_index, -1);
        Stack<Integer> stack = new Stack<Integer>();
        for(int i = n - 1; i >= 0; i--) {
            while(!stack.isEmpty() && a[i] > a[stack.peek()]) {
                int r = stack.peek();
                stack.pop();
                left_index[r] = i;
            }
            stack.push(i);
        }
        return left_index;
    }
    public static int[] nextGreaterInLeft(long a[], int n) {
        int left_index[] = new int[n];
        Arrays.fill(left_index, -1);
        Stack<Integer> stack = new Stack<Integer>();
        for(int i = n - 1; i >= 0; i--) {
            while(!stack.isEmpty() && a[i] > a[stack.peek()]) {
                int r = stack.peek();
                stack.pop();
                left_index[r] = i;
            }
            stack.push(i);
        }
        return left_index;
    }
    public static int[] nextGreaterInRight(int a[], int n) {
        int right_index[] = new int[n];
        Arrays.fill(right_index, n);
        Stack<Integer> stack = new Stack<Integer>();
        for(int i = 0; i < n; i++) {
            while(!stack.isEmpty() && a[i] > a[stack.peek()]) {
                int r = stack.peek();
                stack.pop();
                right_index[r] = i;
            }
            stack.push(i);
        }
        return right_index;
    }
    public static int[] nextGreaterInRight(long a[], int n) {
        int right_index[] = new int[n];
        Arrays.fill(right_index, n);
        Stack<Integer> stack = new Stack<Integer>();
        for(int i = 0; i < n; i++) {
            while(!stack.isEmpty() && a[i] > a[stack.peek()]) {
                int r = stack.peek();
                stack.pop();
                right_index[r] = i;
            }
            stack.push(i);
        }
        return right_index;
    }
    public static int[] nextSmallerInLeft(int a[], int n) {
        int left_index[] = new int[n];
        Arrays.fill(left_index, -1);
        Stack<Integer> stack = new Stack<Integer>();
        for(int i = n - 1; i >= 0; i--) {
            while(!stack.isEmpty() && a[i] < a[stack.peek()]) {
                int r = stack.peek();
                stack.pop();
                left_index[r] = i;
            }
            stack.push(i);
        }
        return left_index;
    }
    public static int[] nextSmallerInLeft(long a[], int n) {
        int left_index[] = new int[n];
        Arrays.fill(left_index, -1);
        Stack<Integer> stack = new Stack<Integer>();
        for(int i = n - 1; i >= 0; i--) {
            while(!stack.isEmpty() && a[i] < a[stack.peek()]) {
                int r = stack.peek();
                stack.pop();
                left_index[r] = i;
            }
            stack.push(i);
        }
        return left_index;
    }
    public static int[] nextSmallerInRight(int a[], int n) {
        int right_index[] = new int[n];
        Arrays.fill(right_index, n);
        Stack<Integer> stack = new Stack<Integer>();
        for(int i = 0; i < n; i++) {
            while(!stack.isEmpty() && a[i] < a[stack.peek()]) {
                int r = stack.peek();
                stack.pop();
                right_index[r] = i;
            }
            stack.push(i);
        }
        return right_index;
    }
    public static int[] nextSmallerInRight(long a[], int n) {
        int right_index[] = new int[n];
        Arrays.fill(right_index, n);
        Stack<Integer> stack = new Stack<Integer>();
        for(int i = 0; i < n; i++) {
            while(!stack.isEmpty() && a[i] < a[stack.peek()]) {
                int r = stack.peek();
                stack.pop();
                right_index[r] = i;
            }
            stack.push(i);
        }
        return right_index;
    }
}

class Fraction implements Comparable<Fraction> {
    
    public long num = 0;
    public long den = 1;

    private static long gcd(long a, long b) {
        for(long rem; b > 0; rem = a % b, a = b, b = rem);
        return a;
    }
    private static long lcm(long a, long b) {
        return (a * b) / gcd(a, b);
    }

    public Fraction(long num) {
        this.num = num;
    }
    public Fraction(long num, long den) {
        this.num = num;
        this.den = den;
        long g = gcd(num, den);
        this.num /= g;
        this.den /= g;
    }
    public static Fraction add(Fraction f1, Fraction f2) {
        long num1 = f1.num;
        long den1 = f2.den;
        long num2 = f2.num;
        long den2 = f2.den;
        long den = lcm(den1, den2);
        return new Fraction(num1 * (den / den1) + num2 * (den / den2), den);
    }
    public static Fraction sub(Fraction f1, Fraction f2) {
        long num1 = f1.num;
        long den1 = f2.den;
        long num2 = f2.num;
        long den2 = f2.den;
        long den = lcm(den1, den2);
        return new Fraction(num1 * (den / den1) - num2 * (den / den2), den);
    }
    public static Fraction mul(Fraction f1, Fraction f2) {
        return new Fraction(f1.num * f2.num, f1.den * f2.den);
    }
    public static Fraction div(Fraction f1, Fraction f2) {
        return new Fraction(f1.num * f2.den, f2.den * f2.num);
    }
    public String toString() {
        if(this.den == 1)
            return Long.toString(this.num);
        return (this.num + "/" + this.den);
    }
    public int compareTo(Fraction f2) {
        Fraction f1 = this;
        long l = lcm(f1.den, f2.den);
        long num1 = (l/f1.den) * f1.num;
        long num2 = (l/f2.den) * f2.num;
        if(num1 == num2)
            return 0;
        return ((num1>num2)?1:-1);
    }
}

class DSU {

    private int size = 0;
    private int parent[];
    private int weight[];

    public DSU(int size) {
        this.size = size + 1;
        this.parent = new int[this.size];
        this.weight = new int[this.size];
        for(int i = 0; i < this.size; i++) {
            parent[i] = i;
            weight[i] = 1;
        }
    }

    public int get(int a) {
        for(; parent[a] != a; parent[a] = parent[parent[a]], a = parent[a]);
        return a;
    }

    public int getParent(int a) {
        return get(a);
    }

    public boolean find(int a, int b) {
        return get(a) == get(b);
    }

    public void join(int a, int b) {
        union(a, b);
    }

    public void union(int a, int b) {
        int parentA = get(a);
        int parentB = get(b);
        if(parentA == parentB)
            return;
        if(weight[parentA] < weight[parentB]) {
            parent[parentA] = parent[parentB];
            weight[parentB] += weight[parentA];
        }
        else {
            parent[parentB] = parent[parentA];
            weight[parentA] += weight[parentB];
        }
    }
}

class Primality {

    public static boolean check(long n) {
        if(n == 0 || n == 1)
            return false;
        else if(n == 2 || n == 3)
            return true;
        else if(n % 2 == 0 || n % 3 == 0)
            return false;
        final int root = ((int)(Math.sqrt(n)));
        for(int i = 5; i <= root; i += 6) {
            if(n % i == 0 || n % (i + 2) == 0)
                return false;
        }
        return true;
    }

    private static long power(long x, long y, long p) {
        long result = 1;
        for(result = 1; y > 0; x = (x % p * x % p) % p, y >>= 1)
            if((y&1) == 1)
                result = (result % p * x % p) % p;
        return result;
    }

    private static boolean check_composite(long n, long a, long d, long s) {
        long x = power(a, d, n);
        if(x == 1 || x == n - 1)
            return false;
        for(int r = 1; r < s; r++) {
            x = (x % n * x % n) % n;
            if(x == n - 1)
                return false;
        }
        return true;
    }

    public static boolean test(long n) {
        if(n < 2)
            return false;
        int r = 0;
        long d = n - 1;
        while((d&1) == 0) {
            d >>= 1;
            r++;
        }
        int primes[] = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37};
        for(int i = 0; i < primes.length; i++) {
            int a = primes[i];
            if(n == a)
                return true;
            if(check_composite(n, a, d, r))
                return false;
        }
        return true;
    }
}

class Sieve {

    public static boolean prime[];
    public static int spf[];
    public static int phi[];
    public static ArrayList<Integer> primes;

    public static boolean[] sieve(int size) {
        prime = new boolean[size];
        Arrays.fill(prime, false);
        prime[2] = true;
        for(int i = 3; i < size; i += 2)
            prime[i] = true;
        for(int i = 3; i <= Math.sqrt(size); i += 2)
            if(prime[i])
                for(int j = i * i; j < size; j += i)
                    prime[j] = false;
        return prime;
    }
    public static ArrayList<Integer> getPrimes(int size) {
        if(prime == null) {
            sieve(size);
        }
        if(primes == null) {
            primes = new ArrayList<Integer>();
            primes.add(2);
            primes.add(3);
            for(int i = 5; i + 2 < size; i += 6) {
                if(prime[i]) {
                    primes.add(i);
                }
                if(prime[i + 2]) {
                    primes.add(i + 2);
                }
            }
        }
        return primes;
    }
    public static int[] SPF(int size) {
        spf = new int[size];
        spf[0] = spf[1] = 1;
        for(int i = 2; i < size; i += 2)
            spf[i] = 2;
        for(int i = 3; i < size; i += 2)
            spf[i] = i;
        for(int i = 3; i < Math.sqrt(size); i += 2)
            if(spf[i] == i)
                for(int j = i * i; j < size; j += i)
                    if(spf[j] == j)
                        spf[j] = i;
        return spf;
    }
    public static int[] getSPF(int size) {
        return SPF(size);
    }
    public static int[] computeSPF(int size) {
        return SPF(size);
    }
    public static int[] Totient(int size) {
        phi = new int[size];
        for(int i = 0; i < size; i++)
            phi[i] = i;
        for(int i = 2; i < size; i++)
            if(phi[i] == i)
                for(int j = i; j < size; j += i)
                    phi[j] -= phi[j]/i;
        return phi;
    }
    public static int[] getTotient(int size) {
        return Totient(size);
    }
    public static int[] computeTotient(int size) {
        return Totient(size);
    }
    public static int numberOfFactors(int n) {
        int ans = 1;
        for(int c = 0; n > 1; c = 0) {
            for(int s = spf[n]; s == spf[n]; n /= s, c++);
            ans *= c + 1;
        }
        return ans;
    }
}

class IO {

    private BufferedReader reader;
    private StringTokenizer st;
    private BufferedWriter writer;
    private BufferedWriter debugWriter;
    public String end = "\n";
    public String sep = " ";

    public IO() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        writer = new BufferedWriter(new OutputStreamWriter(System.out));
        debugWriter = new BufferedWriter(new OutputStreamWriter(System.err));
    }

    public String next() {
        while(st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(reader.readLine());
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }

    public int nextInt() {
        return Integer.parseInt(next());
    }

    public float nextFloat() {
        return Float.parseFloat(next());
    }

    public long nextLong() {
        return Long.parseLong(next());
    }

    public double nextDouble() {
        return Double.parseDouble(next());
    }

    public String nextLine() {
        String str = "";
        try {
            str = reader.readLine();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    public int[] intArray(int n) {
        int a[] = new int[n];
        for(int i = 0; i < n; i++)
            a[i] = nextInt();
        return a;
    }

    public long[] longArray(int n) {
        long a[] = new long[n];
        for(int i = 0; i < n; i++)
            a[i] = nextLong();
        return a;
    }

    public char[] charArray(int n) {
        char a[] = new char[n];
        for(int i = 0; i < n; i++)
            a[i] = next().charAt(0);
        return a;
    }

    public void print(String s) {
        try {
            writer.write(s);
            writer.flush();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void println() {
        print("\n");
    }

    public void newLine() {
        println();
    }

    public void write(Object o) {
        print(o.toString().concat(end));
    }

    public void write(String s) {
        print(s.concat(end));
    }

    public void write(StringBuilder s) {
        s.append(end);
        print(s.toString());
    }

    public void write(String ... a) {
        StringBuilder s = new StringBuilder();
        s.append(a[0]);
        for(int i = 1; i < a.length; i++)
            s.append(sep + a[i]);
        s.append(end);
        print(s.toString());
    }

    public void write(int ... a) {
        StringBuilder s = new StringBuilder();
        s.append(a[0]);
        for(int i = 1; i < a.length; i++)
            s.append(sep + a[i]);
        s.append(end);
        print(s.toString());
    }

    public void write(long ... a) {
        StringBuilder s = new StringBuilder();
        s.append(a[0]);
        for(int i = 1; i < a.length; i++)
            s.append(sep + a[i]);
        s.append(end);
        print(s.toString());
    }
    
    public void write(char ... a) {
        StringBuilder s = new StringBuilder();
        s.append(a[0]);
        for(int i = 1; i < a.length; i++)
            s.append(sep + a[i]);
        s.append(end);
        print(s.toString());
    }

    public <E> void write(E[] a) {
        StringBuilder s = new StringBuilder();
        s.append(a[0]);
        for(int i = 1; i < a.length; i++)
            s.append(sep + a[i].toString());
        s.append(end);
        print(s.toString());
    }

    public <E> void write(List<E> a) {
        Object arr[] = a.toArray();
        write(arr);
    }

    public void error(String s) {
        try {
            debugWriter.write(s);
            debugWriter.flush();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void debug(Object o) {
        error(o.toString() + end);
    }

    public void debug(String s) {
        s = s.concat(end);
        error(s);
    }

    public void debug(String ... a) {
        StringBuilder s = new StringBuilder();
        s.append(a[0]);
        for(int i = 1; i < a.length; i++)
            s.append(sep + a[i]);
        s.append(end);
        error(s.toString());
    }

    public void debug(int ... a) {
        StringBuilder s = new StringBuilder();
        s.append(a[0]);
        for(int i = 1; i < a.length; i++)
            s.append(sep + a[i]);
        s.append(end);
        error(s.toString());
    }

    public void debug(long ... a) {
        StringBuilder s = new StringBuilder();
        s.append(a[0]);
        for(int i = 1; i < a.length; i++)
            s.append(sep + a[i]);
        s.append(end);
        error(s.toString());
    }
    
    public void debug(char ... a) {
        StringBuilder s = new StringBuilder();
        s.append(a[0]);
        for(int i = 1; i < a.length; i++)
            s.append(sep + a[i]);
        s.append(end);
        error(s.toString());
    }

    public <E> void debug(E[] a) {
        StringBuilder s = new StringBuilder();
        s.append(a[0]);
        for(int i = 1; i < a.length; i++)
            s.append(sep + a[i].toString());
        s.append(end);
        error(s.toString());
    }

    public <E> void debug(List<E> a) {
        Object arr[] = a.toArray();
        debug(arr);
    }
}

class Edge implements Comparable<Edge> {

    int start = 0, end = 0, cost = 0;

    Edge(Edge e) {
        this.start = e.start;
        this.end = e.end;
        this.cost = e.cost;
    }
    Edge(int start, int end, int cost) {
        this.start = start;
        this.end = end;
        this.cost = cost;
    }
    public String toString() {
        return "(" + start + ", " + end + ", " + cost + ")";
    }
    public int compareTo(Edge e2) {
        Edge e1 = this;
        int cost1 = e1.cost;
        int cost2 = e2.cost;
        if(cost1 == cost2) {
            if(e1.start == e2.start) {
                if(e1.end == e2.end) {
                    return 0;
                }
                return (e1.end > e2.end ? 1 : -1);
            }
            return (e1.start > e2.start ? 1 : -1);
        }
        return ((cost1 > cost2) ? 1 : -1);
    }
}

class Graph {

    private int V, E;
    private ArrayList<LinkedList<Integer>> graph;
    private Edge edges[];
    private Edge included[];
    private long cost = 0;
    private boolean weighted = false;
    private boolean directed = true;

    public Graph(int V, int E, boolean directed, boolean weighted) {
        this.V = V;
        this.E = E;
        this.weighted = weighted;
        this.directed = directed;

        graph = new ArrayList<LinkedList<Integer>>();
        for(int i = 0; i < this.V; i++)
            graph.add(new LinkedList<Integer>());

        edges = new Edge[E];
    }

    public void addEdge(int a, int b) {
        graph.get(a).add(b);
    }

    public void readGraph(IO io) {

        for(int i = 0; i < this.E; i++) {
            int a = io.nextInt();
            int b = io.nextInt();
            addEdge(a, b);
            if(!directed)
                addEdge(b, a);
            int c = 0;
            if(weighted)
                c = io.nextInt();
            edges[i] = new Edge(a, b, c);
        }
    }

    void MST() {
        included = new Edge[(this.V - 1)];
        Arrays.sort(edges);
        int includedSoFar = 0;
        DSU dsu = new DSU(V);
        int ind = 0;
        while(includedSoFar != (V - 1) && ind < E) {
            if(!dsu.find(edges[ind].start, edges[ind].end)) {
                dsu.union(edges[ind].start, edges[ind].end);
                included[includedSoFar] = edges[ind];
                cost = cost + edges[ind].cost;
                includedSoFar += 1;
            }
            ind += 1;
        }
    }

    long mstCost() {
        if(included == null) {
            MST();
        }
        return cost;
    }

    Edge[] mstIncluded() {
        if(included == null) {
            MST();
        }
        return included;
    }

    public boolean bfs(int start, int end) {

        if(!(0 <= end && end < this.V))
            return false;

        boolean visited[] = new boolean[this.V];
        Arrays.fill(visited, false);
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;
        while(!queue.isEmpty() && !visited[end]) {
            start = queue.poll();
            for(int i: this.graph.get(start)) {
                if(!visited[i]) {
                    visited[i] = true;
                    queue.add(i);
                }
            }
        }
        return visited[end];
    }

}

class Pair implements Comparable<Pair> {
    long x = 0, y = 0;
    Pair(long x, long y) {
        this.x = x;
        this.y = y;
    }
    public int compareTo(Pair p2) {
        Pair p1 = this;
        if(p1.x == p2.x) {
            if(p1.y == p2.y)
                return 0;
            return ((p1.y > p2.y) ? 1 : -1);
        }
        return ((p1.x > p2.x) ? 1 : -1);
    }
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}

class Trie {

    private class Node {
        Node next[];
        boolean isTerminalNode;
        int samePrefixCount;
        Node() {
            next = new Node[26];
            isTerminalNode = false;
            samePrefixCount = 0;
        }
    }

    private Node root;
    private StringBuilder lcp;
    private int count;
    private int lcpLength;
    private char preset;

    public Trie() {
        root = new Node();
        lcp = new StringBuilder();
        count = 0;
        lcpLength = 0;
        preset = 'a';
    }

    public int length() {
        return count;
    }

    public void setPreset(char ch) {
        preset = ch;
    }

    public void insert(String str) {
        if(contains(str)) {
            return;
        }
        _insert(root, str, str.length());
        count++;
        if(count == 1) {
            lcp.append(str);
        }
        else {
            int i = 0;
            final int length = Math.min(lcp.length(), str.length());
            for(; i < length && lcp.charAt(i) == str.charAt(i); i++);
            lcp.setLength(i);
            lcpLength = i;
        }
    }

    private void _insert(Node node, String str, final int length) {
        for(int i = 0; i < length; i++) {
            char ch = str.charAt(i);
            int ind = ((int)(ch - preset));
            if(node.next[ind] == null) {
                node.next[ind] = new Node();
            }
            node.next[ind].samePrefixCount++;
            node = node.next[ind];
        }
        node.isTerminalNode = true;
    }

    public void remove(String str) {
        if(!contains(str)) {
            return;
        }
        _remove(root, str, str.length());
    }

    private void _remove(Node node, String str, final int length) {
        for(int i = 0; i < length; i++) {
            char ch = str.charAt(i);
            int ind = ((int)(ch - preset));
            node = node.next[ind];
        }
        node.isTerminalNode = false;
        count--;
    }

    public boolean contains(String str) {
        return _find(root, str, str.length());
    }

    private boolean _find(Node node, String str, final int length) {
        for(int i = 0; i < length; i++) {
            char ch = str.charAt(i);
            int ind = ((int)(ch - preset));
            if(node.next[ind] == null) {
                return false;
            }
            node = node.next[ind];
        }
        return node.isTerminalNode;
    }
    
    public int prefixCount(String prefix) {
        return _prefixCount(root, prefix, prefix.length());
    }

    private int _prefixCount(Node node, String prefix, final int length) {
        for(int i = 0; i < length; i++) {
            char ch = prefix.charAt(i);
            int ind = ((int)(ch - preset));
            if(node.next[ind] == null) {
                return 0;
            }
            node = node.next[ind];
        }
        return node.samePrefixCount;
    }

    public int longestCommonPrefixLength() {
        return lcpLength;
    }

    public String longestCommonPrefix() {
        return lcp.toString();
    }

}

class SegmentTree {

    /**
     * Segment Tree with Get, pointUpdate, RangeUpdate and RangeQuery Operations
     */

    private long tree[];
    private long carry[];
    private int length;
    private long default_value;

    private long key(long value1, long value2) {
        /**
         * Key Function builds up relation between parent node and its two child nodes
         */
        return value1 + value2;              // Range Sum Queries
        // return Math.min(value1, value2);     // Range Minimum Queries
        // return Math.max(value1, value2);     // Range Maximum Queries
        // return value1 * value2;              // Range Product Queries
        // return value1 | value2;              // Range Bitwise OR Queries
        // return value1 & value2;              // Range Bitwise AND Queries
        // return value1 ^ value2;              // Range Bitwise EX-OR Queries
        // return lcm(value1, value2);          // Range LCM Queries
        // return gcd(value1, value2);          // Range GCD Queries
    }

    private long gcd(long a, long b) {
        /**
         * For GCD Queries
         */
        for(long rem; b > 0; rem = a % b, a = b, b = rem);
        return a;
    }

    private long lcm(long a, long b) {
        /**
         * For LCM Queries
         */
        return (a * b) / gcd(a, b);
    }

    public SegmentTree(int a[]) {
        /**
         * Default Constructor
         */
        this.default_value = 0;
        this.tree = build(a);
        this.length = this.tree.length / 2;
        this.carry = new long[2 * length];
        Arrays.fill(carry, this.default_value);
    }

    public SegmentTree(int a[], long default_value) {
        /**
         * Overloaded Constructor. Extra nodes are filled with Default_value
         */
        this.default_value = default_value;
        this.tree = build(a);
        this.length = this.tree.length / 2;
        this.carry = new long[2 * length];
        Arrays.fill(carry, this.default_value);
    }


    private long[] build(int a[]) {
        /**
         * Builds Segment Tree
         */
        int n = a.length;
        ArrayList<Long> array = new ArrayList<Long>(n);
        for(int i = 0; i < n; i++) {
            array.add(((long)(a[i])));
        }
        while((n & (n - 1)) != 0) {
            array.add(this.default_value);
            n ++;
        }
        long tree[] = new long[2 * n];
        Arrays.fill(tree, this.default_value);
        for(int i = 0; i < n; i++) {
            tree[n + i] = array.get(i);
        }
        for(int i = n - 1; i > 0; i--) {
            tree[i] = key(tree[2 * i], tree[2 * i + 1]);
        }
        return tree;
    }

    public long get(int index) {
        /**
         * Returns the value at a[index]
         */
        return this.rangeQuery(index, index);
    }

    public void pointUpdate(int index, long value) {
        /**
         * Updates a single node and its Ancestors - Sets the node to this value
         */
        pointUpdate(1, 0, this.length - 1, index, value);
    }

    private long pointUpdate(int node, int node_lb, int node_ub, int index, long value) {
        if(index < node_lb || node_ub < index) {
            return this.tree[node];
        }
        else if(node_lb == node_ub && node_ub == index) {
            this.tree[node] = value;
        }
        else {
            long leftReturned = pointUpdate(2 * node, node_lb, (node_lb + node_ub) / 2, index, value);
            long rightReturned = pointUpdate(2 * node + 1, (node_lb + node_ub) / 2 + 1, node_ub, index, value);
            this.tree[node] = key(leftReturned, rightReturned);
        }
        return this.tree[node];
    }

    public void rangeUpdate(int range_lb, int range_ub, long value) {
        /**
         * Updates Several Nodes using the results of Key Function over Children
         */
        rangeUpdate(1, 0, this.length - 1, range_lb, range_ub, value);
    }

    private long rangeUpdate(int node, int node_lb, int node_ub, int range_lb, int range_ub, long value) {
        if(range_lb <= node_lb && node_ub <= range_ub) {
            this.carry[node] = this.key(this.carry[node], value);
        }
        else if(node_ub < range_lb || range_ub < node_lb) {
            return this.carry[node];
        }
        else {
            rangeUpdate(2 * node, node_lb, (node_lb + node_ub) / 2, range_lb, range_ub, value);
            rangeUpdate(2 * node + 1, (node_lb + node_ub) / 2 + 1, node_ub, range_lb, range_ub, value);
        }
        return this.carry[node];
    }
    
    public long rangeQuery(int query_lb, int query_ub) {
        /**
         * Returns the result of Key Function over the range
         */
        return rangeQuery(1, 0, this.length - 1, query_lb, query_ub);
    }

    private long rangeQuery(int node, int node_lb, int node_ub, int query_lb, int query_ub) {
        if(query_lb <= node_lb && node_ub <= query_ub) {
            return this.key(this.tree[node], this.carry[node]);
        }
        else if(query_ub < node_lb || node_ub < query_lb) {
            return this.default_value;
        }
        else {
            long leftReturned = rangeQuery(2 * node, node_lb, (node_lb + node_ub) / 2, query_lb, query_ub);
            long rightReturned = rangeQuery(2 * node + 1, (node_lb + node_ub) / 2 + 1, node_ub, query_lb, query_ub);
            long returned = this.key(this.key(leftReturned, rightReturned), this.carry[node]);
            return returned;
        }
    }
}