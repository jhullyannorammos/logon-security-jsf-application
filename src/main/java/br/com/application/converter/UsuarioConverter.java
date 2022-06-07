package br.com.application.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.com.application.model.Usuario;
import br.com.application.repository.UsuarioRepository;

@FacesConverter(forClass = Usuario.class)
public class UsuarioConverter implements Converter {

	@Inject
	private UsuarioRepository usuarios;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Usuario retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			retorno = this.usuarios.porId(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Usuario usuario = (Usuario) value;
			return usuario == null || usuario.getId() == null ? 
					null : usuario.getId().toString();
		}
		return "";
	}
}