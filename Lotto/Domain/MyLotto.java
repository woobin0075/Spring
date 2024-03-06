package Lotto.Domain;

import java.util.*;

public class MyLotto implements LottoInterface{

    private String name;
    private String id;
    private String password;
    private int papersMany; //구매한 종이 수
    public int[][][] mylottoNumbers = new int[100][5][6];
    private int mypay; //지불한 금액

    public void setId(String id){
        this.id = id;

    }

    public MyLotto(int papersMany){
        this.papersMany = papersMany;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public String getId(){
        return id;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public void setPapersMany(int papersMany){
        this.papersMany = papersMany;
    }

    public int getPapersMany(){
        return papersMany;
    }

    public void setMypay(int mypay){
        this.mypay = mypay;
    }

    public int getMypay(){
        return mypay;
    }


    @Override
    public void passiveLotto() {
        Scanner sc = new Scanner(System.in);

        for(int i=0; i<papersMany; i++){
            System.out.println(i+1+"장");
            for(int j=0; j<5; j++){
                System.out.printf(j+1+"번 로또 번호 : ");
                for(int k=0; k<6; k++){
                    mylottoNumbers[i][j][k] = sc.nextInt();
                }

            }
            System.out.println();
        }
    }

    @Override
    public void autoLotto() {
        Random rand = new Random();

        for(int i=0; i<papersMany; i++){
            for(int j=0; j<5; j++){

                for(int k=0; k<6; k++){
                    mylottoNumbers[i][j][k] = rand.nextInt(50)+1;
                }

                if(findsameNumbers(mylottoNumbers[i][j], i, j-1) && j>0){
                    j--;
                }

            }

        }
    }

    @Override
    public void semiautoLotto(int inputNumbers) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        for(int i=0; i<papersMany; i++){
            System.out.println(i+1+"장");
            for(int j=0; j<5; j++){
                System.out.printf(j+1+"번 로또 번호 : ");
                for(int k=0; k<inputNumbers; k++){
                    mylottoNumbers[i][j][k] = sc.nextInt();
                }

                for(int k2=inputNumbers; k2 < 6; k2++){
                    mylottoNumbers[i][j][k2] = rand.nextInt(50)+1;
                }

                System.out.println(Arrays.toString(mylottoNumbers[i][j]));
            }
            System.out.println();
        }
    }

    private boolean findsameNumbers(int[] lottoNumbers, int paperi, int beforej){
        int same = 0;

        for(int i=0; i< 5; i++){
            for(int j=0; j<=beforej; j++){
                if(mylottoNumbers[paperi][i][j] == lottoNumbers[j]){
                    same++;
                }
            }

            if(same == 6){
                return true;
            }
            same = 0;
        }

        return false;
    }

    public void printMyLottoNumbers(){
        System.out.println("나의 총 로또 번호 목록");
        for(int i=0; i<papersMany; i++){
            for(int j=0; j<5; j++){

                for(int k=0; k<6; k++){
                    System.out.printf("%d ",mylottoNumbers[i][j][k]);
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
