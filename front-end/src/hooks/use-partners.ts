import { useEffect, useState } from "react";
import { usePartnerStore } from "../store/use-partner-store.ts";

export function usePartners(participacaoMin?: number) {
  const { partners, loading, error, fetchPartners } = usePartnerStore();
  const [participation, setParticipation] = useState<number>(
    participacaoMin || 0,
  );

  useEffect(() => {
    if (partners.length === 0) {
      fetchPartners(participacaoMin);
    }
  }, [participacaoMin]);

  useEffect(() => {
    if (participation >= 0) {
      fetchPartners(participation);
    }
  }, [participation]);

  return { partners, loading, error, setParticipation };
}
