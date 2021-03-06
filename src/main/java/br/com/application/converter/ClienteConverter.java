package br.com.application.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.com.application.model.Cliente;
import br.com.application.repository.ClienteRepository;

@FacesConverter(forClass = Cliente.class)
public class ClienteConverter implements Converter {

	@Inject
	private ClienteRepository clientes;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Cliente retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			retorno = this.clientes.porId(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Cliente cliente = (Cliente) value; 
			return cliente != null && cliente.getId() != null ? cliente.getId().toString() : null;
		}
		return "";
	}

}