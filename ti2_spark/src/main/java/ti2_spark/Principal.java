package ti2_spark;

import java.util.*;

public class Principal {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // Conexão com UsuarioDAO
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        if (!usuarioDAO.conectar()) {
            System.out.println("Falha na conexão com o banco!");
            return;
        }
        
        // Menu de opções CRUD
        int op = 0;
        do {
            System.out.println("\nMENU DE OPÇÕES\n");
            System.out.println("1 - Listar usuários");
            System.out.println("2 - Inserir usuário");
            System.out.println("3 - Atualizar usuário");
            System.out.println("4 - Excluir usuário");
            System.out.println("5 - Sair\n");
            System.out.print("Escolha a opção desejada: ");
            op = sc.nextInt();
            sc.nextLine();
            switch (op) {
                case 1: listarUsuarios(usuarioDAO); break;
                case 2: inserirUsuario(usuarioDAO); break;
                case 3: atualizarUsuario(usuarioDAO); break;
                case 4: excluirUsuario(usuarioDAO); break;
                case 5:
                    usuarioDAO.close();
                    sc.close();
                    System.out.println("Conexão encerrada.");
                    break;
                default: System.out.println("Opção inválida!"); break;
            }
        } while (op != 5);
    }

    // Mostrar os usuários do sistema
    private static void listarUsuarios(UsuarioDAO usuarioDAO) {
        Usuario[] usuarios = usuarioDAO.listarUsuarios();
        if (usuarios != null && usuarios.length > 0) {
            for (Usuario u : usuarios) System.out.println(u.toString());
        } else System.out.println("Nenhum usuário encontrado.");
    }

    // Inserir um novo usuário ao sistema
    private static void inserirUsuario(UsuarioDAO usuarioDAO) {
        System.out.print("Informe o novo login: ");
        String log = sc.nextLine();
        System.out.print("Informe a nova senha: ");
        String sen = sc.nextLine();
        sen = DAO.getMD5(sen);
        System.out.print("Informe o novo sexo (M/F): ");
        char sex = sc.nextLine().charAt(0);
        Usuario[] usuarios = usuarioDAO.listarUsuarios();
        int cod = (usuarios != null && usuarios.length > 0) ? usuarios[usuarios.length - 1].getCodigo() + 1 : 1;
        Usuario novo = new Usuario(cod, log, sen, sex);
        if (usuarioDAO.inserirUsuario(novo)) System.out.println("Usuário inserido com sucesso!");
    }

    // Alterar usuário informado
    private static void atualizarUsuario(UsuarioDAO usuarioDAO) {
        System.out.print("Informe o codigo do usuario que deseja alterar: ");
        int cod = sc.nextInt();
        sc.nextLine();
        Usuario u = buscarUsuarioPorCodigo(usuarioDAO, cod);
        if (u == null) {
            System.out.println("Usuário de código " + cod + " não encontrado!");
            return;
        }
        System.out.print("Informe o novo login: ");
        String log = sc.nextLine();
        System.out.print("Informe a nova senha: ");
        String sen = sc.nextLine();
        sen = DAO.getMD5(sen);
        System.out.print("Informe o novo sexo (M/F): ");
        char sex = sc.nextLine().charAt(0);
        Usuario alt = new Usuario(cod, log, sen, sex);
        if (usuarioDAO.atualizarUsuario(alt)) System.out.println("Usuário atualizado com sucesso!");
    }

    // Excluir usuário informado
    private static void excluirUsuario(UsuarioDAO usuarioDAO) {
        System.out.print("Informe o codigo do usuario que deseja excluir: ");
        int cod = sc.nextInt();
        sc.nextLine();
        Usuario u = buscarUsuarioPorCodigo(usuarioDAO, cod);
        if (u == null) {
            System.out.println("Usuário com código " + cod + " não encontrado!");
            return;
        }
        if (usuarioDAO.excluirUsuario(cod)) System.out.println("Usuário excluído com sucesso!");
    }

    // Método auxiliar para buscar usuário por código
    private static Usuario buscarUsuarioPorCodigo(UsuarioDAO dao, int cod) {
        Usuario[] usuarios = dao.listarUsuarios();
        if (usuarios != null) {
            for (Usuario u : usuarios) if (u.getCodigo() == cod) return u;
        }
        return null;
    }
}
