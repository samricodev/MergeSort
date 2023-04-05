package mergesort;

import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import javax.swing.*;

public class MergeSort extends JFrame {

    JButton btnLimpiar, btnMergeSort, btnGenerar, btnForkJoin, btnExecute;
    JTextArea txtTam, txtInfo, txtInfo2, txtInfo3;
    JLabel info, merge, generar, msMerge, msFork, msExec;
    JScrollPane scp, scp2, scp3;

    public int nums[];

    public void generarRandom() {
        int tam = Integer.parseInt(txtTam.getText());
        this.nums = new int[tam];

        for (int i = 0; i < tam; i++) {
            nums[i] = (int) (Math.random() * 100);
        }

        imprimirArray(nums, txtInfo);
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

    public void ordenacionMergeSort(int arr[], int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;
            ordenacionMergeSort(arr, l, m);
            ordenacionMergeSort(arr, m + 1, r);
            combinarVector(arr, l, m, r);
        }
    }

    public void imprimirArray(int[] array, JTextArea txt) {
        String res = "";
        for (int i = 0; i < array.length; i++) {
            res += array[i] + " ";
        }
        txt.setText(res);
    }

    public void limpiar() {
        txtInfo.setText("");
        txtInfo2.setText("");
        txtInfo3.setText("");
        txtTam.setText("");
        msFork.setText("");
        msMerge.setText("");
        msExec.setText("");
    }

    public MergeSort() {
        setTitle("Merge Sort");
        setSize(800, 800);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        info = new JLabel("Ingresa el numero de datos a crear: ");
        info.setBounds(10, 10, 250, 20);
        add(info);

        txtTam = new JTextArea();
        txtTam.setBounds(250, 10, 100, 20);
        add(txtTam);

        generar = new JLabel("Generado");
        generar.setBounds(10, 20, 100, 100);
        add(generar);

        merge = new JLabel("Ordenado");
        merge.setBounds(500, 20, 100, 100);
        add(merge);
        
        txtInfo = new JTextArea();
        add(txtInfo);
        
        scp = new JScrollPane(txtInfo);
        scp.setBounds(10, 100, 200, 50);
        add(scp);

        txtInfo2 = new JTextArea();
        add(txtInfo2);
        
        scp2 = new JScrollPane(txtInfo2);
        scp2.setBounds(500, 100, 200, 50);
        add(scp2);
        
        txtInfo3 = new JTextArea();
        add(txtInfo3);
        
        scp3 = new JScrollPane(txtInfo3);
        scp3.setBounds(500, 250, 200, 50);
        add(scp3);

        msMerge = new JLabel();
        msMerge.setBounds(100, 400, 100, 100);
        add(msMerge);

        msFork = new JLabel();
        msFork.setBounds(100, 450, 100, 100);
        add(msFork);
        
        msExec = new JLabel();
        msExec.setBounds(100, 500, 100, 100);
        add(msExec);

        btnGenerar = new JButton("Generar");
        btnGenerar.setBounds(10, 600, 100, 40);
        btnGenerar.addActionListener(e -> generarRandom());
        add(btnGenerar);

        btnMergeSort = new JButton("MergeSort");
        btnMergeSort.setBounds(150, 600, 100, 40);
        btnMergeSort.addActionListener(e -> {
            long inicio = System.currentTimeMillis();
            ordenacionMergeSort(nums, 0, nums.length - 1);
            long fin = System.currentTimeMillis();
            msMerge.setText("Merge: " + (fin - inicio) + " ms");
            imprimirArray(nums, txtInfo2);
        });
        add(btnMergeSort);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(300, 600, 100, 40);
        btnLimpiar.addActionListener(e -> limpiar());
        add(btnLimpiar);

        btnForkJoin = new JButton("ForkJoin");
        btnForkJoin.setBounds(450, 600, 100, 40);
        btnForkJoin.addActionListener((ActionEvent e) -> {
            ForkJoinPool fjPool = new ForkJoinPool();
            long inicio = System.currentTimeMillis();
            fjPool.invoke(new ForkJoin(nums, 0, nums.length - 1));
            long fin = System.currentTimeMillis();
            msFork.setText("ForkJoin: " + (fin - inicio) + " ms");
            imprimirArray(nums, txtInfo2);
        });
        add(btnForkJoin);
        
        btnExecute = new JButton("Executor");
        btnExecute.setBounds(600,600,100,40);
        btnExecute.addActionListener( e -> {
            ExecutorService executor = Executors.newFixedThreadPool(2);
            long inicio = System.currentTimeMillis();
            executor.execute(new ExeServ(nums, 0, nums.length - 1));
            long fin = System.currentTimeMillis();
            msExec.setText("Executor: " + (fin - inicio) + " ms");
            imprimirArray(nums, txtInfo3);
        });
        add(btnExecute);
    }

    public static void main(String[] args) {
        MergeSort m = new MergeSort();
        m.setVisible(true);
    }
}
