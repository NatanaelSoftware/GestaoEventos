    package br.com.eventos.model;

    import java.time.LocalDateTime;

    public class IngressoMeia extends Ingresso {

        private String comprovacaoMeia;

        public IngressoMeia(int id, Evento evento, String compradorNome, String comprovacaoMeia) {
            super(id, evento, "MEIA", compradorNome, "EMITIDO", LocalDateTime.now(), evento.getValorBase() / 2);

            this.comprovacaoMeia = comprovacaoMeia;
        }

        //Getter
        public String getComprovacaoMeia() {return comprovacaoMeia;}
        //Fim getter

        //setter
        public void setComprovacaoMeia(String comprovacaoMeia) {this.comprovacaoMeia = comprovacaoMeia;}
        //Fim setter

        @Override
        public double calcularPreco() {
            return getEvento().getValorBase() / 2;
        }

        @Override
        public String validarEntrada() {
            System.out.println("Validando ingresso MEIA para o comprador: " + getCompradorNome());
            return super.validarEntrada();
        }
    }