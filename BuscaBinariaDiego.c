#include <stdio.h>
#include <string.h>

typedef struct _Endereco Endereco;

struct _Endereco{
	char logradouro[72];
	char bairro[72];
	char cidade[72];
	char uf[72];
	char sigla[2];
	char cep[8];
	char lixo[2];
};

void achaCep (long ultimo, FILE *f, char* cep){

	
	int qt;
	int i = 0;
	long inicio = 0;
	long meio;
	Endereco e;
	
	while (inicio <= ultimo){
		
		i++;
		meio = (inicio + ultimo)/2;
		
		fseek(f, meio*sizeof(Endereco), SEEK_SET);
		qt = fread(&e, sizeof(Endereco), 1, f);
		
		if(strncmp(cep, e.cep, 8) == 0){ 
			printf("%.72s\n%.72s\n%.72s\n%.72s\n%.2s\n%.8s\n",e.logradouro,e.bairro,e.cidade,e.uf,e.sigla,e.cep);
			break;
		}
		
		else{
			if (strncmp(cep,e.cep,8) < 0){
				ultimo = meio - 1;
			}
			else{
				inicio = meio + 1;
			}
		}
	}
	printf("Quantidade de interações: %d\n", i);

}
	
int main(int argc, char**argv)
{
	FILE *f;
	Endereco e;
	long posicao, ultimo;

	
	if(argc != 2)
	{
		fprintf(stderr, "USO: %s [CEP]", argv[0]);
		return 1;
	}
	
	
	f = fopen("cep_ordenado.dat","r");
	fseek(f,0,SEEK_END);
	posicao = ftell(f);
	rewind(f);

	
	ultimo = (posicao/sizeof(Endereco)) - 1;

	achaCep(ultimo, f, argv[1]);

	fclose(f);

	return 0;

}

