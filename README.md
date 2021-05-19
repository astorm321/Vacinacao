# Vacinação

<p> Este projeto consiste em criar uma aplicaçao móvel onde as organizações de saúde podem armazenar os dados sobre a vacinação da populaçao 
criando uma ficha do paciente na qual guardam os dados pessoais e relevantes , onde têm as vacinas existentes no mercado, por fim resulta numa tabela com a compilação de todos os dados
relativos à vacinação . </p>

## Os Objetivos aplicação :checkered_flag:

:trophy:-Ter uma tabela com os dados todos do paciente

:trophy:-Ter uma tabela com a informação da vacina (Fabricante origem validade efeitos secundários )

:trophy:Ter uma tabela com os dados organizados, com o Nome do paciente ( um ID tambem ) , o ID da vacina , o dia e hora em que tomou a primeira dose 
	      o dia e hora da segunda dose ( caso a vacina seja de duas doses ) e incluindo também se for o caso os efeitos que a vacina teve no paciente


> Status do Projeto: Em desenvolvimento :warning:

### Lista de funcionalidades
  - Dados do paciente ( com as operações CRUD ) / Mostrar os dados do paciente 
  ( ID, Nome , Morada , Contacto , Idade , nr de utente , altura , peso , faixa etaria , profissao , alguma doença que seja relevante ou histórico da familia)
 
  - Dados da vacina (com as operações CRUD ) / Mostrar os dados da vacina 
  ( ID , Nome da vacina , fabricante da vacina , validade da vacina , Doses necessarias , quantas vacinas ainda estao disponiveis)
  
  - Ficha de Vacinação (com as operações CRUD ) / Mostrar os dados da ficha de vacinação 
  (ID_Paciente , ID_Vacina , data e hora da vacinação , Dose 1 ou dose 2 , efeitos que o paciente teve 
  
 
 #### Paciente : 
|ID|Nome|Morada|Contacto|Idade|Nr_Utente|Altura|Peso|Faixa Etaria| Profissao|HistoricoMedico|
| -------- | -------- | -------- | -------- | -------- | -------- | -------- | -------- | -------- | -------- | -------- |
|1|Vitor|Rua_Guarda|200000000|25|999999999|180cm|90|Jovem|Estudante|....|

##### Vacina: 
|ID|Name Da Vacina |Fabricante |Validade |Nr de doses recomendadas|Ainda disponiveis|
| -------- | -------- | -------- | -------- | -------- | -------- |
|1|Vacina BioNteck|Pfizer|25/10/2021|2|1240

###### Ficha Vacinação
|ID_Paciente|ID_VACINA|Data/Hora Vacinação Dose1|Data/Hora Vacinação Dose2|Efeitos Secundarios Observados|
|-------- | -------- | -------- | -------- | -------- |
|1|1| 15:01 19/05/2021| ----| Febre e dores corporais|
