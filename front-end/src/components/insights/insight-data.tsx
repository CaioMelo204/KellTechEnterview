import React from "react";
import { CnaeAccordeon } from "../accordeon/cnae-accordeon.tsx";
import styles from "./insight-data.module.css";

interface InsightProps {
  data: {
    nome: string;
    cnpj: string;
    participacao: string;
    map: string;
    cnaes: {
      subClasse: string;
      descricao: string;
    }[];
  };
}

export const InsightData: React.FC<InsightProps> = ({ data }) => {
  return (
    <div className={styles.insight}>
      <div className={styles.partnerData}>
        <div className={styles.partnerHeader}>
          <h2>Sócio</h2>
          <p>{data.nome}</p>
        </div>
        <div className={styles.partnerHeader}>
          <h2>CNPJ</h2>
          <p>{data.cnpj}</p>
        </div>
        <div className={styles.partnerHeader}>
          <h2>Participação</h2>
          <p>{data.participacao}%</p>
        </div>
      </div>

      <CnaeAccordeon data={data.cnaes} />

      <div className={styles.partnerMap}>
        <iframe
          src={data.map}
          width="600"
          height="450"
          loading="lazy"
          referrerPolicy="no-referrer-when-downgrade"
          title="Mapa do parceiro"
        ></iframe>
      </div>
    </div>
  );
};
