package com.danilo.forum.modelo;

public enum StatusTopico {
	
	NAO_RESPONDIDO, // quando o topico for criado, nasce como não respondido
	NAO_SOLUCIONADO, // quando for respondido, o autor do questionamento precisa verificar se foi solucionado
	SOLUCIONADO, // o autor do questionamento marca como solucionado
	FECHADO; // depois que estiver fechado, não é mais possível editar

}
