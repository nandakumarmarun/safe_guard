package com.security.analyzer.v1.dashboard;

import com.security.analyzer.v1.securitytest.SecurityTestResource;
import com.security.analyzer.v1.test.SecurityTestResponseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardResource {

    private final Logger log = LoggerFactory.getLogger(SecurityTestResource.class);

    private final DashboardService DashboardService;

    @GetMapping("/chart")
    public ResponseEntity<ChartDTO> GetchartData() {
        log.debug("REST request to get all SecurityTests");
        return ResponseEntity.ok().body(DashboardService.getChart());
    }


    @GetMapping("/topten")
    public ResponseEntity<List<SecurityTestResponseDTO>> getAllTestStatus(
            @PathVariable(value = "id", required = false) final String  id
    ) throws URISyntaxException {
        log.debug("REST request to update SecurityTest : {}, {}", id);
        var securityTestResponseDTOS = DashboardService.findAllTestslimited();
        return ResponseEntity.ok().body(securityTestResponseDTOS);
    }



    @PutMapping("/update-status/{id}")
    public void updateSecurityTestStatus(
            @PathVariable(value = "id", required = false) final String id
    ) throws URISyntaxException {
        log.debug("REST request to update SecurityTest : {}, {}", id);
        DashboardService.UpdateStatus(id);
    }



}
