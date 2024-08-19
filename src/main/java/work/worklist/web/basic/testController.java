package work.worklist.web.basic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
/*
    String 구조적인 부분으로 어려움을 느끼고 있다.

 */
@Controller
@RequiredArgsConstructor
public class testController {
    @GetMapping("/basic/testController")
    public String getTest(Model model){
        return "basic/test";
    }
    @PostMapping("/basic/testController")
    public String postTest(Model model){
        return "basic/test";
    }
}
