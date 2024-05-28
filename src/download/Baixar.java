package download;

import com.sankhya.util.SessionFile;
import com.sankhya.util.UIDGenerator;
import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.ws.ServiceContext;

public class Baixar implements AcaoRotinaJava{

	@Override
	public void doAction(ContextoAcao ctx) throws Exception {
		SessionHandle hnd = null;
		Registro[] registros = ctx.getLinhas();
		Registro r = registros[0];
		//NOME DO ARQUIVO SESSAO
		String chave = "jar_" + UIDGenerator.getNextID();
		try {
			hnd = JapeSession.open();

		
			//CAMPO ARQUIVO DA TABELA É ONDE TEM O JAR
			Object jar = r.getCampo("ARQUIVO");
			
			if(jar != null) {
				//BYTE ARRAY DO ARQUIVO QUE QUER BAIXAR 
				byte[] fileContent = (byte[])jar;
				
				
				SessionFile sessionFile = SessionFile.createSessionFile(chave+".jar", "application/jar", fileContent);
	
				
				ServiceContext.getCurrent().putHttpSessionAttribute(chave, sessionFile);
			}

		} catch(Exception ex) {
			ctx.mostraErro(ex.getMessage());
		} finally {
			JapeSession.close(hnd);
		}
		
		ctx.setMensagemRetorno("<p><strong>Atenção: Ao baixar o arquivo salve como .jar</strong></p>"
				+ "<p>Use site como: http://www.javadecompilers.com para descompilar </p>"
				+ "<a id=\"alink\" href=\"/mge/visualizadorArquivos.mge?chaveArquivo=" 
				+ chave 
				+ "&ignoraPodeExportarjar=N&forcarDownload=N\" target=\"_blank\">Baixar jar</a>");

	}
}
