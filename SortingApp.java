import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SortingApp extends JFrame {
    private ArrayList<Student> studentList = new ArrayList<>();
    private JTextArea outputArea;
    private JComboBox<String> sortingMethod;
    private JComboBox<String> prodiComboBox;
    
    public SortingApp() {
        
        setTitle("Sorting Application");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel nimLabel = new JLabel("NIM:");
        JTextField nimField = new JTextField(10);

        JLabel nameLabel = new JLabel("Nama:");
        JTextField nameField = new JTextField(10);

        JLabel prodiLabel = new JLabel("Prodi:");
        String[] prodiOptions = {
                "Teknologi Informasi", "Sistem Informasi", 
                "Pendidikan Teknologi Informasi", 
                "Teknik Informatika", "Teknik Komputer"
        };
        prodiComboBox = new JComboBox<>(prodiOptions);

        JButton addButton = new JButton("Add Student");
        JButton sortButton = new JButton("Sort");

        String[] sortOptions = {"Selection Sort", "Insertion Sort"};
        sortingMethod = new JComboBox<>(sortOptions);

        outputArea = new JTextArea(15, 40);
        outputArea.setEditable(false);

        add(nimLabel);
        add(nimField);
        add(nameLabel);
        add(nameField);
        add(prodiLabel);
        add(prodiComboBox);
        add(addButton);
        add(sortingMethod);
        add(sortButton);
        add(new JScrollPane(outputArea));

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nim = nimField.getText().trim();
                String name = nameField.getText().trim();
                String prodi = (String) prodiComboBox.getSelectedItem();

                if (!nim.isEmpty() && !name.isEmpty() && prodi != null) {
                    studentList.add(new Student(nim, name, prodi));
                    outputArea.append("Added: NIM=" + nim + ", Name=" + name + ", Prodi=" + prodi + "\n");
                    nimField.setText("");
                    nameField.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill all fields");
                }
            }
        });

     
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sortingMethod.getSelectedItem().equals("Selection Sort")) {
                    selectionSort();
                } else {
                    insertionSort();
                }
                displayStudents();
            }
        });
    }

    private void selectionSort() {
        int n = studentList.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (getProdiIndex(studentList.get(j).getProdi()) < getProdiIndex(studentList.get(minIndex).getProdi())) {
                    minIndex = j;
                }
            }
           
            Student temp = studentList.get(minIndex);
            studentList.set(minIndex, studentList.get(i));
            studentList.set(i, temp);
        }
    }

    private void insertionSort() {
        int n = studentList.size();
        for (int i = 1; i < n; i++) {
            Student key = studentList.get(i);
            int j = i - 1;

            while (j >= 0 && getProdiIndex(studentList.get(j).getProdi()) > getProdiIndex(key.getProdi())) {
                studentList.set(j + 1, studentList.get(j));
                j = j - 1;
            }
            studentList.set(j + 1, key);
        }
    }

    private int getProdiIndex(String prodi) {
        switch (prodi) {
            case "Teknologi Informasi":
                return 1;
            case "Sistem Informasi":
                return 2;
            case "Pendidikan Teknologi Informasi":
                return 3;
            case "Teknik Informatika":
                return 4;
            case "Teknik Komputer":
                return 5;
            default:
                return Integer.MAX_VALUE;
        }
    }

    private void displayStudents() {
        outputArea.setText("Sorted Students:\n");
        for (Student student : studentList) {
            outputArea.append("NIM: " + student.getNim() + ", Name: " + student.getName() + ", Prodi: " + student.getProdi() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SortingApp app = new SortingApp();
            app.setVisible(true);
        });
    }

    class Student {
        private String nim;
        private String name;
        private String prodi;

        public Student(String nim, String name, String prodi) {
            this.nim = nim;
            this.name = name;
            this.prodi = prodi;
        }

        public String getNim() {
            return nim;
        }

        public String getName() {
            return name;
        }

        public String getProdi() {
            return prodi;
        }
    }
}
