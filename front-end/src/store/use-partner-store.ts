import { create } from "zustand";
import { partnerService } from "../services/partner-service.ts";
import type { PartnerResumeInterface } from "../interfaces/partner-resume.interface.ts";
import type { PartnerDetailInterface } from "../interfaces/partner-detail.interface.ts";

interface PartnerState {
  partners: PartnerResumeInterface[];
  selectedPartner: PartnerDetailInterface | null;
  loading: boolean;
  error: string | null;
  fetchPartners: (participacaoMin?: number) => Promise<void>;
  fetchPartnerByCnpj: (cnpj: string) => Promise<void>;
  clear: () => void;
  clearSelectedPartner: () => void;
}

export const usePartnerStore = create<PartnerState>((set) => ({
  partners: [],
  selectedPartner: null,
  loading: false,
  error: null,

  fetchPartners: async (participacaoMin) => {
    set({ loading: true, error: null });
    try {
      const data = await partnerService.getAll(participacaoMin);
      set({ partners: data });
    } catch (error: any) {
      set({ error: error.message || "Erro ao buscar sócios" });
    } finally {
      set({ loading: false });
    }
  },

  fetchPartnerByCnpj: async (cnpj) => {
    set({ loading: true, error: null });
    try {
      const partner = await partnerService.getByCnpj(cnpj);
      set({ selectedPartner: partner });
    } catch (error: any) {
      set({ error: error.message || "Erro ao buscar sócio" });
    } finally {
      set({ loading: false });
    }
  },

  clearSelectedPartner: async () => {
    set({ loading: false, selectedPartner: null });
  },

  clear: () => set({ partners: [], selectedPartner: null }),
}));
