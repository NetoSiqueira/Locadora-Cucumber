package service;

import model.Filme;
import model.NotaAluguel;
import util.DateUtil;
import util.TipoCategoria;


public class AluguelService {
    public NotaAluguel alugar(Filme filme, TipoCategoria tipo) {
        NotaAluguel nota = new NotaAluguel();
        if (filme.getEstoque() == 0)
            throw new RuntimeException("Filme sem estoque");

        switch (tipo){
            case COMUM:
                nota.setPreco(filme.getAluguel());
                nota.setDataEntrega(DateUtil.obterDiasDiferente(1));
                nota.setPontuacao(1);
                break;
            case SEMANAL:
                nota.setPreco(filme.getAluguel() * 3);
                nota.setDataEntrega(DateUtil.obterDiasDiferente(7));
                nota.setPontuacao(3);
                break;
            case EXTENDIDO:
                nota.setPreco(filme.getAluguel() * 2);
                nota.setDataEntrega(DateUtil.obterDiasDiferente(3));
                nota.setPontuacao(2);
                break;
        }
            filme.setEstoque(filme.getEstoque()-1);
       return nota;

    }

}
