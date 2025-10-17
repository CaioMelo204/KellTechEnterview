export interface Cnae {
  subclasse: string;
  descricao: string;
}

export interface PartnerDetailInterface {
  id: string;
  nome: string;
  cnpj: string;
  participacao: number;
  map: string;
  cnaes: Cnae[];
}
