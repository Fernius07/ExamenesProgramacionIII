package es.deusto.prog3.examen202401ord.logica;

import java.util.Random;

/** Chateador castellano no basado en GPT (devuelve frases aleatorias)
 * @author prog3 @ ingenieria.deusto.es
 */
public class ChatNoGPT {
	protected static String[] frases = {
		    "¿Podrías explicarme más sobre eso?",
		    "Eso suena interesante. Cuéntame más.",
		    "¿Qué te hace pensar eso?",
		    "Eso es realmente único. ¿Por qué lo crees?",
		    "Nunca había escuchado algo así. ¿Cómo llegaste a esa idea?",
		    "Eso es un buen punto. ¿Qué más piensas al respecto?",
		    "¿Puedes profundizar en ese tema?",
		    "Me intriga lo que dices. ¿Puedes dar más detalles?",
		    "¿Cómo te sientes acerca de eso?",
		    "Eso es bastante peculiar. ¿Por qué lo mencionas?",
		    "¿Y qué más?",
		    "Eso es bastante curioso. ¿Puedes explicar por qué?",
		    "¿Qué otros pensamientos tienes al respecto?",
		    "Esa es una perspectiva interesante. ¿Cómo la desarrollaste?",
		    "Me pregunto qué te llevó a esa conclusión.",
		    "Eso suena complicado. ¿Puedes simplificarlo?",
		    "Sería interesante que me dieras un ejemplo de eso.",
		    "Eso parece importante para ti. ¿Por qué es así?",
		    "¿Cómo llegaste a saber eso?",
		    "¿Eso te preocupa de alguna manera?",
		    "¿Qué significa eso para ti?",
		    "Eso es un enfoque único. ¿Cómo lo ves tú?",
		    "¿Has pensado en otras alternativas?",
		    "Eso me hace pensar. ¿Tienes más información?",
		    "¿Cómo afecta eso tu vida diaria?",
		    "¿Eso cambió tu forma de ver las cosas?",
		    "¿Hay algo más que quieras añadir?",
		    "¿Eso te ha pasado a ti?",
		    "¿Cómo manejas situaciones así?",
		    "Eso suena desafiante. ¿Cómo te sientes al respecto?",
		    "¿Qué te inspiró a pensar eso?",
		    "Eso parece que te apasiona. ¿Por qué?",
		    "¿Hay algún detalle que puedas compartir?",
		    "Eso parece complicado. ¿Cómo lo manejas?",
		    "¿Podrías contarme más sobre tu experiencia con eso?",
		    "¿Eso te sorprendió de alguna manera?",
		    "Eso suena como algo que te importa mucho. ¿Correcto?",
		    "¿Cómo te relacionas con eso?",
		    "Eso debe haber sido una experiencia interesante. ¿Cómo fue?",
		    "¿Podrías explicar cómo llegaste a esa idea?",
		    "Eso suena como un gran cambio. ¿Cómo lo enfrentaste?",
		    "¿Qué consejo darías sobre eso?",
		    "Eso parece ser un tema importante. ¿Qué piensas?",
		    "¿Cómo te adaptaste a esa situación?",
		    "Eso suena como un dilema. ¿Qué hiciste?",
		    "¿Qué aprendiste de esa experiencia?",
		    "Eso debe haber sido difícil. ¿Cómo lo superaste?",
		    "¿Cómo afectó eso tus decisiones futuras?",
		    "Eso parece una lección valiosa. ¿Qué piensas?",
		    "¿Eso te hizo cambiar de opinión sobre algo?",
		    "¿Cómo balanceas eso con tus otras responsabilidades?",
		    "Eso parece requerir mucha reflexión. ¿Cómo lo abordaste?",
		    "¿Eso influyó en tu forma de pensar?",
		    "¿Qué impacto tuvo eso en tu vida?",
		    "Eso parece ser un punto de inflexión. ¿Cómo lo manejaste?",
		    "¿Cómo te preparaste para eso?",
		    "Eso suena como una decisión difícil. ¿Qué te llevó a ella?",
		    "¿Qué te motivó a actuar de esa manera?",
		    "Es relevante. Cuéntame más al respecto.",
		    "Eso parece haber sido una experiencia enriquecedora. ¿Correcto?",
		    "¿Qué desafíos enfrentaste en ese proceso?",
		    "Eso parece haber requerido mucho coraje. ¿Cómo lo encontraste?",
		    "¿Cómo mantuviste tu enfoque durante eso?",
		    "Eso debe haber cambiado tu perspectiva. ¿Cómo?",
		    "¿Qué sacrificios tuviste que hacer?",
		    "Eso parece ser un logro significativo. ¿Cómo te sientes al respecto?",
		    "¿Cómo mantienes tu motivación en situaciones así?",
		    "Eso suena como que te exigió mucho. ¿Cómo lo manejaste?",
		    "¿Qué estrategias utilizaste para enfrentar eso?",
		    "Eso debe haber sido una revelación. ¿Cómo reaccionaste?",
		    "¿Cómo influyó eso en tus relaciones con los demás?",
		    "Eso suena como un momento de aprendizaje. ¿Qué obtuviste de él?",
		    "¿Cómo te ha ayudado eso a crecer?",
		    "Tienes que sentir orgullo por cómo te has comportado.",
		    "Eso parece ser un tema complejo. ¿Cómo lo desglosarías?",
		    "Me interesa el impacto que crees que eso tendrá a largo plazo.",
		    "Eso suena como una experiencia única. ¿Cómo te ha moldeado?",
		    "¿Cómo abordas nuevos desafíos relacionados con eso?",
		    "Eso parece ser una fuente de inspiración. ¿Es así?",
		    "¿Cómo equilibras tus emociones en situaciones como esa?",
		    "Eso debe haber sido un punto de reflexión. ¿Qué conclusiones sacaste?",
		    "¿Cómo te mantienes positivo frente a desafíos como ese?",
		    "Eso parece haber sido un momento crucial. ¿Cómo lo abrazaste?",
		    "¿Qué te ayudó a mantener la perspectiva en esa situación?",
		    "Eso suena como que te ha enseñado mucho. ¿Puedes compartir algo?",
		    "¿Cómo te mantienes enfocado en tus objetivos durante tiempos difíciles?",
		    "Eso parece haber sido una experiencia que te cambió. ¿Cómo?",
		    "Me interroga lo que me comentas, no sé interpretarlo.",
		    "¿Qué te ha dado fuerzas durante momentos como ese?",
		    "Eso suena como una oportunidad para crecer. ¿Lo fue?",
		    "¿Cómo encuentras claridad en situaciones confusas?"
	};

	private static Random random = new Random();
	
	/** Devuelve respuesta en castellano a la pregunta formulada
	 * @param pregunta	Pregunta formulada por el usuario
	 * @return	Respuesta del sistema de chat automático
	 */
	public String devuelveRespuesta( String pregunta ) {
		return frases[ random.nextInt(frases.length) ];
	}
	
}
