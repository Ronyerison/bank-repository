# Projeto API Banco

Este projeto simula a API de um banco. A API foi desenvolvida em java utilizando o framework VRaptor e contem as seguintes funcionalidades:
 - Cadastro de um banco ou instituição financeira: esta funcionalidade por opção está aberta de autenticação para que possam ser realizados cadastros de instituição financeira.
 - Cadastro de uma agencia: funcionalidade também aberta de autenticação para que sejam realizados os cadastros das agencias pertences aos bancos.
 - Cadastro de clientes: funcionalidade aberta de autenticação para cadastro dos clientes dos bancos.
 - Autenticação: a autenticação da API deve é necessária para realizar transações, ou seja, cada cliente do banco deverá se autenticar (passando número da conta e senha) para realizar operações na conta (Saques, Depositos, Transferencias). Autenticação de clientes segue protocolo OAuth 2.0.
 - Contas: As operações permitidas em contas são: saques, depositos, transferencias entre contas do mesmo banco e transferencias entre contas de bancos diferentes.
 
     

CONFIGURANDO PROJETO

1. O `servlet container` utilizado foi Tomcat 7.
2. Banco de Dados utilizado `MySql`: criar esquema `bank`

