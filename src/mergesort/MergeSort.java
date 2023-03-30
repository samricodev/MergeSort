package mergesort;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.concurrent.ForkJoinPool;
import javax.swing.*;

public class MergeSort extends JFrame {

    JButton btnLimpiar, btnMergeSort, btnGenerar, btnForkJoin, btnExecute;
    JPanel canvaNorte, canvaSur, canvaEste, canvaOeste, canvaCentro;
    JTextArea txtTam, txtInfo, txtInfo2;
    JLabel info, merge, generar, msMerge, msFork;
    JScrollPane scp, scp2;

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
        txtTam.setText("");
        msFork.setText("");
        msMerge.setText("");
    }

    public MergeSort() {
        setTitle("Merge Sort");
        setSize(700, 700);
        setResizable(false);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        canvaNorte = new JPanel();
        canvaNorte.setBounds(100, 100, 300, 300);
        canvaNorte.setLayout(new BorderLayout());
        add("North", canvaNorte);

        canvaSur = new JPanel();
        canvaSur.setBounds(100, 100, 300, 300);
        add("South", canvaSur);

        canvaEste = new JPanel();
        canvaEste.setBounds(100, 100, 300, 300);
        canvaEste.setLayout(new BorderLayout());
        add("East", canvaEste);

        canvaOeste = new JPanel();
        canvaOeste.setBounds(100, 100, 300, 300);
        canvaOeste.setLayout(new BorderLayout());
        add("West", canvaOeste);

        canvaCentro = new JPanel();
        canvaCentro.setBounds(100, 100, 300, 300);
        canvaCentro.setLayout(new BorderLayout());
        add("Center", canvaCentro);

        generar = new JLabel("Generado");
        generar.setBounds(100, 100, 100, 100);
        canvaOeste.add("North", generar);

        merge = new JLabel("Ordenado");
        merge.setBounds(100, 100, 100, 100);
        canvaEste.add("North", merge);

        info = new JLabel("Ingresa el numero de datos a crear: ");
        info.setBounds(100, 100, 100, 50);
        canvaNorte.add("West", info);

        txtTam = new JTextArea();
        txtTam.setBounds(100, 100, 100, 50);
        canvaNorte.add("Center", txtTam);

        txtInfo = new JTextArea();
        txtInfo.setBounds(100, 100, 200, 50);
        txtInfo.setLineWrap(true);
        canvaOeste.add("Center", txtInfo);

        txtInfo2 = new JTextArea();
        txtInfo2.setBounds(100, 100, 200, 50);
        txtInfo2.setLineWrap(true);
        canvaEste.add("Center", txtInfo2);

        msMerge = new JLabel();
        msMerge.setBounds(100, 100, 100, 100);
        canvaCentro.add("Center", msMerge);

        msFork = new JLabel();
        msFork.setBounds(100, 100, 100, 100);
        canvaCentro.add("South", msFork);

        btnGenerar = new JButton("Generar");
        btnGenerar.setBounds(100, 100, 100, 50);
        btnGenerar.addActionListener(e -> generarRandom());
        canvaSur.add(btnGenerar);

        btnMergeSort = new JButton("MergeSort");
        btnMergeSort.setBounds(100, 100, 100, 50);
        btnMergeSort.addActionListener(e -> {
            long inicio = System.currentTimeMillis();
            ordenacionMergeSort(nums, 0, nums.length - 1);
            long fin = System.currentTimeMillis();
            msMerge.setText("Merge: " + (fin - inicio) + " milisegundos");
            imprimirArray(nums, txtInfo2);
        });
        canvaSur.add(btnMergeSort);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(100, 100, 100, 50);
        btnLimpiar.addActionListener(e -> limpiar());
        canvaSur.add(btnLimpiar);

        btnForkJoin = new JButton("ForkJoin");
        btnForkJoin.setBounds(100, 100, 100, 50);
        btnForkJoin.addActionListener((ActionEvent e) -> {
            ForkJoinPool fjPool = new ForkJoinPool();
            long inicio = System.currentTimeMillis();
            fjPool.invoke(new ForkJoin(nums, 0, nums.length - 1));
            long fin = System.currentTimeMillis();
            msFork.setText("ForkJoin: " + (fin - inicio) + " milisegundos");
            imprimirArray(nums, txtInfo2);
        });
        canvaSur.add(btnForkJoin);
    }

    public static void main(String[] args) {
        MergeSort m = new MergeSort();
        m.setVisible(true);
    }
}
