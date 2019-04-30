package com.cqhc.modules.system.rest;

import com.cqhc.aop.log.Log;
import com.cqhc.exception.BadRequestException;
import com.cqhc.modules.system.domain.ParameterConfig;
import com.cqhc.modules.system.domain.Unit;
import com.cqhc.modules.system.repository.UnitRepository;
import com.cqhc.modules.system.service.ParameterConfigService;
import com.cqhc.modules.system.service.UnitService;
import com.cqhc.modules.system.service.dto.ParameterConfigDTO;
import com.cqhc.modules.system.service.dto.UnitDTO;
import com.cqhc.modules.system.service.query.ParameterConfigQueryService;
import com.cqhc.modules.system.service.query.UnitQueryService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @author huicheng
* @date 2019-04-24
*/
@RestController
@RequestMapping("api")
public class ParameterConfigController {

    @Autowired
    private ParameterConfigService parameterConfigService;

    @Autowired
    private ParameterConfigQueryService parameterConfigQueryService;

    private static final String ENTITY_NAME = "parameterConfig";

    @Log("查询ParameterConfig")
    @GetMapping(value = "/parameterConfig")
    @ApiOperation(value = "查询参数",notes = "关键字可根据类型名称、描述查询")
    @PreAuthorize("hasAnyRole('ADMIN','PARAMETER_CONFIG_SELECT','PARAMETER_CONFIG_ALL')")
    public ResponseEntity getParameterConfigs(ParameterConfigDTO resources, Pageable pageable){
        return new ResponseEntity(parameterConfigQueryService.queryAll(resources,pageable),HttpStatus.OK);
    }

    @Log("新增ParameterConfig")
    @PostMapping(value = "/parameterConfig")
    @ApiOperation(value = "新增参数",notes = "由单位ID=-1的新增参数")
    @ApiImplicitParams({@ApiImplicitParam(name = "name",value = "参数名",required = true,dataType ="String"),
            @ApiImplicitParam(name = "value",value = "参数值",required = true,dataType ="String"),
            @ApiImplicitParam(name = "remark",value = "描述",dataType ="String")})
    @PreAuthorize("hasAnyRole('ADMIN','PARAMETER_CONFIG_CREATE')")
    public ResponseEntity create(@Validated @RequestBody ParameterConfig resources){
        //新增时判断该ID是否已经存在
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }

        //判断参数类型是否为单位级。1代表单位级.
        if(resources.getType()==1){
            List<Unit> unitList=parameterConfigService.findUnit();
            for (Unit unit:unitList){
                resources.setUnit(unit);
                parameterConfigService.create(resources);
            }
        }

        return new ResponseEntity(parameterConfigService.create(resources),HttpStatus.CREATED);

    }

    @Log("修改ParameterConfig")
    @PutMapping(value = "/parameterConfig")
    @ApiOperation(value = "修改参数",notes = "只能修改参数的值")
    @ApiImplicitParam(name = "value",value = "参数值",required = true,dataType ="String")
    @PreAuthorize("hasAnyRole('ADMIN','PARAMETER_CONFIG_ALL','PARAMETER_CONFIG_EDIT')")
    public ResponseEntity update(@Validated @RequestBody ParameterConfig resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME +" ID Can not be empty");
        }
        parameterConfigService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除ParameterConfig")
    @DeleteMapping(value = "/parameterConfig/{id}")
    @ApiOperation(value = "删除参数")
    @PreAuthorize("hasAnyRole('ADMIN','PARAMETER_CONFIG_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        //只有单位ID=-1的才能删除。判断
        if (id!=-1){
            throw new BadRequestException("不能删除！");
        }
        parameterConfigService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}