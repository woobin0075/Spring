package work.worklist;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/basic")
public class ControllerTest {

    @GetMapping("link")
    public String linkTest(Model model){
        model.addAttribute("data1", "dataTest1");
        model.addAttribute("data2", "dataTest2");
        return "basic/link";
    }
    @GetMapping("/form-data1")
    public String formData1(Model model) {
        DataTest dataTest1 = new DataTest("철수", 20);
        DataTest dataTest2 = new DataTest("영희", 30);
        DataTest dataTest3 = new DataTest("동현", 31);

        List<DataTest> dataTests = new ArrayList<>();
        dataTests.add(dataTest1);
        dataTests.add(dataTest2);
        dataTests.add(dataTest3);

        Map<String, DataTest> dataTestMap = new HashMap<>();
        dataTestMap.put("dataTest1", dataTest1);
        dataTestMap.put("dataTest2", dataTest2);
        dataTestMap.put("dataTest3", dataTest3);

        model.addAttribute("dataTest1", dataTest1);
        model.addAttribute("dataTest2", dataTest2);
        model.addAttribute("dataTest3", dataTest3);
        model.addAttribute("dataTests", dataTests);
        model.addAttribute("dataTestMap", dataTestMap);

        return "basic/form-data1";
    }
    @GetMapping("/form-data2")
    public String formData2(HttpSession session){
        session.setAttribute("testData", "TestData");
        return "basic/form-data2";
    }
    @Component("helloTestDataFun")
    static class helloTestData{
        public String hello(String data){
            return "hello " + data;
        }
    }

    @GetMapping("/date-form1")
    public String stringDate(Model model){
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "basic/date-form1";
    }

    @Data
    static class DataTest{
        private String userName;
        private int age;

        public DataTest(String userName, int age){
            this.userName = userName;
            this.age = age;
        }
    }
}