import { ClickableDataGrid } from "./components/table/ClickableDataGrid.tsx";
import styles from "./App.module.css";
import { InsightData } from "./components/insights/insight-data.tsx";
import { usePartners } from "./hooks/use-partners.ts";
import { usePartnerByCnpj } from "./hooks/use-partner-by-cnpj.ts";
import { useState } from "react";

function App() {
  const { partners, loading, setParticipation } = usePartners();
  const {
    selectedPartner,
    fetchPartnerByCnpj,
    loading: loadingCnpj,
    setSelectedCnpj: setCnpj,
  } = usePartnerByCnpj(null);
  const [inputParticipation, setInputParticipation] = useState<number>(0);
  const [selectedCnpj, setSelectedCnpj] = useState<string | null>(null);

  const handleSelectCnpj = async (cnpj: string) => {
    await fetchPartnerByCnpj(cnpj);
    setSelectedCnpj(cnpj);
  };

  const handleSubmit = async () => {
    if (inputParticipation < 0) {
      return;
    }
    setSelectedCnpj(null);
    setCnpj(null);
    setParticipation(inputParticipation);
  };

  return (
    <div className={styles.container}>
      <div className={styles.title}>Societario Insight</div>

      <hr className={styles.divider} />

      <div className={styles.participationContainer}>
        <p>Participação Minima (%)</p>
        <input
          className={styles.participationInput}
          type="number"
          value={inputParticipation}
          onChange={(e) => setInputParticipation(Number(e.target.value))}
          placeholder="Digitar"
        />
        <button onClick={handleSubmit} className={styles.participationButton}>
          Pesquisar
        </button>
      </div>

      <hr className={styles.divider} />

      {loading !== true || partners.length > 0 ? (
        <div className={styles.cnpjContainer}>
          <ClickableDataGrid
            data={partners}
            onSelectedCnpj={handleSelectCnpj}
          />
        </div>
      ) : (
        <></>
      )}

      {selectedCnpj !== null && !loadingCnpj ? (
        <InsightData data={selectedPartner}></InsightData>
      ) : (
        <></>
      )}
    </div>
  );
}

export default App;
