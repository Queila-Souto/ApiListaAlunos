package com.listaVip.cadastro.aluno.service;

import com.listaVip.cadastro.aluno.entity.Aluno;
import com.listaVip.cadastro.aluno.repository.AlunoRepository;
import com.listaVip.cadastro.security.detail.UserDetailsImpl;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @Transactional
    public Aluno create(Aluno aluno) {
        try {
            return alunoRepository.save(aluno);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Violação de integridade: " + ex.getMostSpecificCause().getMessage(),
                    ex
            );
        }
    }

    @Transactional(readOnly = true)
    public List<Aluno> findAll() {
        return alunoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Aluno> findAlunoByUser() {
        Long userId = ((UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal())
                .getId();

        return alunoRepository.findByUsuarioId(userId);
    }

    @Transactional(readOnly = true)
    public Aluno findById(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Aluno não encontrado com id: " + id));
    }

    @Transactional
    public Aluno update(Long id, Aluno aluno) {
        Aluno existing = alunoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Aluno não encontrado com id: " + id));

        existing.setPrimeiroNome(aluno.getPrimeiroNome());
        existing.setSobrenome(aluno.getSobrenome());
        existing.setCurso(aluno.getCurso());
        existing.setTelefone(aluno.getTelefone());

        try {
            return alunoRepository.save(existing);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Violação de integridade: " + ex.getMostSpecificCause().getMessage(),
                    ex
            );
        }
    }

    @Transactional
    public void delete(Long id) {
        boolean exists = alunoRepository.existsById(id);
        if (!exists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Aluno não encontrado com id: " + id);
        }
        alunoRepository.deleteById(id);
    }
}
