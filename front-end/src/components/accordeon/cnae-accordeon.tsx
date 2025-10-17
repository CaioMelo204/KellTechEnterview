import React, { useState } from "react";
import styles from "./cnae-accordeon.module.css";

interface CnaeItem {
  subclasse: string;
  descricao: string;
}

interface CnaeProps {
  data: CnaeItem[];
}

export const CnaeAccordeon: React.FC<CnaeProps> = ({ data }) => {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <div className={styles.container}>
      <button
        onClick={() => setIsOpen(!isOpen)}
        className={styles.headerButton}
      >
        CNAEs {isOpen ? "▲" : "▼"}
      </button>

      {data.length >= 1 && (
        <div className={styles.cnaeContent}>
          <p>
            <strong>{data[0].subclasse}</strong> — {data[0].descricao}
          </p>
        </div>
      )}

      {isOpen && data.length > 1 && (
        <div className={styles.expandable}>
          {data.slice(1).map((cnae, index) => (
            <div key={index} className={styles.cnaeContent}>
              <p>
                <strong>{cnae.subclasse}</strong> — {cnae.descricao}
              </p>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};
