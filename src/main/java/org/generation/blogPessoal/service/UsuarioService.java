package org.generation.blogPessoal.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.generation.blogPessoal.model.Usuario;
import org.generation.blogPessoal.model.UsuarioLogin;
import org.generation.blogPessoal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	private static String encriptadorDeSenha(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}
	
	
	public Optional<Object> cadastrarUsuario(Usuario usuario) {
		return repository.findByUsuario(usuario.getUsuario()).map(usuarioExistente -> {
			return Optional.empty();
		}).orElseGet(() -> {
			usuario.setSenha(encriptadorDeSenha(usuario.getSenha()));
			return Optional.ofNullable(repository.save(usuario));
		});
	}
	
	public Optional<Usuario> atualizarUsuario(Usuario usuario){
		return repository.findById(usuario.getId()).map(usuarioExistente -> {
			usuarioExistente.setNome(usuario.getNome());
			usuarioExistente.setSenha(encriptadorDeSenha(usuario.getSenha()));
			return Optional.ofNullable(repository.save(usuarioExistente));
		}).orElseGet(() -> {
			return Optional.empty();
		});
	}

	public Optional<UsuarioLogin> logar(Optional<UsuarioLogin> _usuario) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuario = repository.findByUsuario(_usuario.get().getUsuario());

		if (usuario.isPresent()) {
			if (encoder.matches(_usuario.get().getSenha(), usuario.get().getSenha())) {

				String auth = _usuario.get().getUsuario() + ":" + _usuario.get().getSenha();
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);

				_usuario.get().setUsuarioID(usuario.get().getId());
				_usuario.get().setToken(authHeader);
				_usuario.get().setNome(usuario.get().getNome());
				_usuario.get().setSenha(usuario.get().getSenha());

				return _usuario;

			}
		}
		return null;
	}

}