package steps;


import io.cucumber.datatable.DataTable;
import model.Filme;
import model.NotaAluguel;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;
import service.AluguelService;
import util.TipoCategoria;


import java.util.Calendar;
import java.util.Date;
import java.util.Map;


public class AlugarFilmeSteps {

  private Filme filme = new Filme();
  private AluguelService aluguel = new AluguelService();
  private NotaAluguel nota;
  private  String erro;
  private TipoCategoria tipoAlugel;

    @Dado("um filme com estoque de {int} unidades")
    public void umFilmeComEstoqueDeUnidades(int int1) {

        filme.setEstoque(int1);
    }
    @Dado("que o preco do aluguel seja R$ {int}")
    public void queOPrecoDoAlguelSejaR$(int aluguel) {
      filme.setAluguel(aluguel);

    }
    @Quando("alugar")
    public void alugar() {
      try {
        nota = aluguel.alugar(filme, tipoAlugel);
      }catch (RuntimeException e){
        erro = e.getMessage();
      }


    }
    @Entao("o preco do aluguel sera R$ {int}")
    public void oPrecoDoAluguelSeraR$(int int1) {
      Assert.assertEquals(int1, nota.getPreco());
    }

    @Entao("o estoque do filme sera {int} unidade")
    public void oEstoqueDoFilmeSeraUnidade(int int1) {
      Assert.assertEquals(int1, filme.getEstoque());
    }
  @Entao("nao sera possivel por falta de estoque")
  public void nao_sera_possivel_por_falta_de_estoque() {
      Assert.assertEquals("Filme sem estoque", erro);
  }

  @Dado("^que o tipo de aluguel seja (.*)$")
  public void que_o_tipo_de_aluguel_seja_extendido(String tipo) {
    tipoAlugel = tipo.equals("semanal")? TipoCategoria.SEMANAL: tipo.equals("extendido")? TipoCategoria.EXTENDIDO: TipoCategoria.COMUM;
  }

  @Entao("^a data de entrega sera em (\\d) dias?$")
  public void a_data_de_entrega_sera_em_dias(int int1) {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DAY_OF_MONTH, int1);

    Date dataRetorno = nota.getDataEntrega();
    Calendar calRetorno = Calendar.getInstance();
    calRetorno.setTime(dataRetorno);
    Assert.assertEquals(cal.get(Calendar.DAY_OF_MONTH), calRetorno.get(Calendar.DAY_OF_MONTH));
    Assert.assertEquals(cal.get(Calendar.MONTH), calRetorno.get(Calendar.MONTH));
    Assert.assertEquals(cal.get(Calendar.YEAR), calRetorno.get(Calendar.YEAR));



  }
  @Entao("a pontuacao recebida sera {int} pontos")
  public void a_pontuacao_recebida_sera_pontos(int int1) {
      Assert.assertEquals(int1, nota.getPontuacao());

  }


  @Dado("um filme")
  public void um_filme(DataTable dataTable) {
     Map<String, String> map = dataTable.asMap(String.class, String.class) ;
     filme = new Filme();
     filme.setEstoque(Integer.parseInt(map.get("estoque")));
     filme.setAluguel(Integer.parseInt(map.get("preco")));
     String tipo = map.get("tipo");
    tipoAlugel = tipo.equals("semanal")? TipoCategoria.SEMANAL: tipo.equals("extendido")? TipoCategoria.EXTENDIDO: TipoCategoria.COMUM;
  }


}
