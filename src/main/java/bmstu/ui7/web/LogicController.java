package bmstu.ui7.web;

import groovy.json.JsonOutput;
import groovy.json.JsonSlurper;
import groovy.util.logging.Slf4j;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import iu7.service.Mamdani;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/ndlogic")
@Slf4j
public class LogicController {

    @Autowired
    private Mamdani mamdani;

    @ApiOperation(value = "Считывание лингвистических переменных", tags = "readV", authorizations = @Authorization("basic"))
    @ApiResponses({
            @ApiResponse(code = 200, message = "ЛП считаны верно", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Неверный формат данных", response = ResponseEntity.class),
    })
    @PostMapping("/vars")
    void readV(@RequestBody Object data) {
        mamdani.readVars(data);
    }

    @ApiOperation(value = "Считывание правил", tags = "readR", authorizations = @Authorization("basic"))
    @ApiResponses({
            @ApiResponse(code = 200, message = "Правила считаны верно", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Неверный формат данных", response = ResponseEntity.class),
    })
    @PostMapping("/rules")
    void readR(@RequestBody Object data) {
        mamdani.readRule(data);
    }

    @ApiOperation(value = "Считывание функций", tags = "readF", authorizations = @Authorization("basic"))
    @ApiResponses({
            @ApiResponse(code = 200, message = "ЛП считаны верно", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Неверный формат данных", response = ResponseEntity.class),
    })
    @PostMapping("/funcs")
    void readF(@RequestBody Object data) {
        mamdani.readFunction(data);
    }

    @ApiOperation(value = "Обратный логиченский вывод", tags = "checkLogic", authorizations = @Authorization("basic"))
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Неверный формат данных", response = ResponseEntity.class),
    })
    @GetMapping("method={method_id}&h={h_value}&d={d_value}")
    double checkLogic(@PathVariable("method_id") int method_id, @PathVariable("h_value") double h, @PathVariable("d_value") double d) {
        double value = 0;
        if (method_id == 0)
            value = mamdani.calculate(h,d,"m");
        // тут задел
        return value;
    }
}
