package com.caiomelo204.backend.controller;

import com.caiomelo204.backend.dto.PartnerDetailedDto;
import com.caiomelo204.backend.dto.PartnerResumeDto;
import com.caiomelo204.backend.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/socios")
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;

    @GetMapping
    public ResponseEntity<List<PartnerResumeDto>> searchByParticipation(
            @RequestParam(name = "participacaoMin", required = true) Double participationMin) {

        List<PartnerResumeDto> socios = partnerService.findAllByParticipation(participationMin);
        return ResponseEntity.ok(socios);
    }

    @GetMapping("/{cnpj}")
    public ResponseEntity<PartnerDetailedDto> detalharPorCnpj(
            @PathVariable("cnpj") String cnpj) {

        PartnerDetailedDto socio = partnerService.findByCnpJ(cnpj);
        return ResponseEntity.ok(socio);
    }
}
