package ResutuantOrguugdanOrCalclation.Domain;

public class Calculator {
    float num1;
    float num2;

    public Calculator(float num1, float num2){
        this.num1 = num1;
        this.num2 = num2;
    }

    public void clac(int select){
        float ans;
        String result;
        String result_num1 = String.format("%.3f", num1);
        String result_num2 = String.format("%.3f", num2);

        switch (select){
            case 1: //더하기
                ans = num1+num2;
                result = String.format("%.3f", ans);
                System.out.println(result_num1+" + "+result_num2+" = "+result);
                break;

            case 2: //빼기
                ans = num1-num2;
                result = String.format("%.3f", ans);
                System.out.println(result_num1+" - "+result_num2+" = "+result);
                break;

            case 3: //곱하기
                ans = num1*num2;
                result = String.format("%.3f", ans);
                System.out.println(result_num1+" * "+result_num2+" = "+result);
                break;

            case 4: //나누기
                ans = num1/num2;
                result = String.format("%.3f", ans);
                System.out.println(result_num1+" / "+result_num2+" = "+result);
        }
    }
}
