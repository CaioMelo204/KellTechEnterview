import { useEffect, useState } from "react";
import { usePartnerStore } from "../store/use-partner-store.ts";

export function usePartnerByCnpj(cnpj: string | null) {
  const {
    selectedPartner,
    loading,
    error,
    fetchPartnerByCnpj,
    clearSelectedPartner,
  } = usePartnerStore();
  const [selectedCnpj, setSelectedCnpj] = useState<string | null>(cnpj);

  useEffect(() => {
    if (cnpj === null) {
      clearSelectedPartner();
    } else if (!selectedPartner || selectedPartner.cnpj !== cnpj) {
      fetchPartnerByCnpj(cnpj);
    }
  }, [cnpj, selectedCnpj]);

  return {
    selectedPartner,
    loading,
    error,
    fetchPartnerByCnpj,
    setSelectedCnpj,
  };
}
