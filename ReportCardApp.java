import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by joshua on 2/7/16.
 */
public class ReportCardApp {

    private static final int MAX_GRADE = 100;
    private static final int MIN_GRADE = 60;

    enum Grade {
        A(96), B(92), C(85), D(80), E(75), F(60);

        private int minGrade;

        Grade(int grade) {
            this.minGrade = grade;
        }

        public static Grade getGrade(int grade) throws Exception {
            if (grade < MIN_GRADE || grade > MAX_GRADE) {
                throw new Exception(String.format("The grade should be a number from %d to %d.",
                        MIN_GRADE, MAX_GRADE));
            }

            for (Grade item: values()) {
                if (grade >= item.minGrade) {
                    return item;
                }
            }
            return null;
        }
    }

    class SubjectGrade {
        private String subject;

        private Grade grade;

        public SubjectGrade(String subject, Grade grade) {
            this.subject = subject;
            this.grade = grade;
        }

        public String getSubject() {
            return subject;
        }

        public Grade getGrade() {
            return grade;
        }
    }

    class ReportCard {
        private static final String PADDING = "...............";
        private int year;

        private SubjectGrade[] grades;

        public ReportCard(int year, SubjectGrade[] grades) {
            this.year = year;
            this.grades = grades;
        }

        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer();
            sb.append(String.format("Report Card for Year %s\n\n", year));

            for (SubjectGrade grade: grades) {
                String dots = PADDING.substring(grade.getSubject().length());
                sb.append(String.format("%s: %s %s\n", grade.getSubject(), dots, grade.getGrade()));
            }
            return sb.toString();
        }

        public void displayReportCard() {
            System.out.println(this.toString());
        }
    }

    public static int getNumber(BufferedReader br, String prompt) throws IOException {
        System.out.print(prompt);

        String input = "";
        try {
            input = br.readLine();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.err.println("Invalid number: " + input);
            return getNumber(br, prompt);
        }
    }

    public static Grade getGradeInput(BufferedReader br, String prompt) {
        Grade grade = null;
        while (grade == null) {
            try {
                grade = Grade.getGrade(getNumber(br, prompt));
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
        return grade;
    }

    public static void main(String[] args) throws IOException {
        new ReportCardApp().run();
    }

    public void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int year = getNumber(br, "Enter report card year: ");
        int numSubjects = getNumber(br, "Enter number of subjects: ");
        SubjectGrade[] grades = new SubjectGrade[numSubjects];

        for (int i = 0; i< numSubjects; i++) {
            System.out.print("Enter subject code: ");
            String subjectCode = br.readLine();
            Grade grade = getGradeInput(br, "Enter grade: ");
            grades[i] = new SubjectGrade(subjectCode, grade);
        }

        ReportCard card = new ReportCard(year, grades);
        System.out.println("\n");
        card.displayReportCard();
    }
}
