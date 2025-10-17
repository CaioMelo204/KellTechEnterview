import { apiClient } from "../api/api.ts";
import type { PartnerResumeInterface } from "../interfaces/partner-resume.interface.ts";
import type { PartnerDetailInterface } from "../interfaces/partner-detail.interface.ts";
import { replaceNotNumber } from "../utils/replace-not-number.ts";

export const partnerService = {
  async getAll(participacaoMin?: number): Promise<PartnerResumeInterface[]> {
    const params = participacaoMin
      ? { participacaoMin }
      : { participacaoMin: 0 };
    const { data } = await apiClient.get("/socios", {
      params,
    });
    return data.map((partnerResume: PartnerResumeInterface) => {
      return {
        ...partnerResume,
        id: replaceNotNumber(partnerResume.cnpj),
      };
    });
  },

  async getByCnpj(cnpj: string): Promise<PartnerDetailInterface> {
    const { data } = await apiClient.get(`/socios/${cnpj}`);
    const response: PartnerDetailInterface = {
      cnpj: data.cnpj,
      participacao: data.participacao,
      id: data.cnpj,
      nome: data.nome,
      map: data.mapUrlEmbed,
      cnaes: [...data.cnaes],
    };

    return response;
  },
};
