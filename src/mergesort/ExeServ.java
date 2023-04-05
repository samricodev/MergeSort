package mergesort;

public class ExeServ implements Runnable{

    private int vec [];
    private int l, r;

    public ExeServ(int[] vec, int l, int r) {
        this.vec = vec;
        this.l = l;
        this.r = r;
    }
    
    
    public void combinarVector(int arr[], int l, int m, int r) {
        
        int n1 = m - l + 1;
        int n2 = r - m;

        int L[] = new int[n1];
        int R[] = new int[n2];
        
        for (int i = 0; i < n1; ++i) {
            L[i] = arr[l + i];
        }
        
        for (int j = 0; j < n2; ++j) {
            R[j] = arr[m + 1 + j];
        }
        
        int i = 0, j = 0;
        int k = l;
        
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
        
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
       
    
    @Override
    public void run() {
        if (l < r) {
            int m = l + (r - l) / 2;
            new ExeServ(vec, l, m);
            new ExeServ(vec, m + 1, r);
            combinarVector(vec, l, m, r);
        }
    }
    
}
