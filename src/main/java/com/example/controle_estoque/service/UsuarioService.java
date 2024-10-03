package com.example.controle_estoque.service;

import com.example.controle_estoque.entity.Papel;
import com.example.controle_estoque.entity.Usuario;
import com.example.controle_estoque.exceptions.PapelNaoEncontradoException;
import com.example.controle_estoque.exceptions.UsuarioExisteException;
import com.example.controle_estoque.exceptions.UsuarioNaoExisteException;
import com.example.controle_estoque.repository.PapelRepository;
import com.example.controle_estoque.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PapelRepository papelRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PapelRepository papelRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.papelRepository = papelRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario cadastrarUsuario(Usuario usuario, String nomePapel) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new UsuarioExisteException("Email já cadastrado.");
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        Papel papel = papelRepository.findByNome(nomePapel)
                .orElseThrow(() -> new PapelNaoEncontradoException("Papel não encontrado: " + nomePapel));
        usuario.setPapeis(Set.of(papel));

        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario consultarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoExisteException("Usuário não encontrado."));
    }

    public Usuario atualizarUsuario(Long id, Usuario usuario) {
        Usuario usuarioSalvo = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoExisteException("Usuário não encontrado."));

        usuarioSalvo.setNome(usuario.getNome());
        usuarioSalvo.setEmail(usuario.getEmail());
        usuarioSalvo.setPapeis(usuario.getPapeis());

        return usuarioRepository.save(usuarioSalvo);
    }

    public void deletarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoExisteException("Usuário não encontrado."));

        usuarioRepository.delete(usuario);
    }
}
