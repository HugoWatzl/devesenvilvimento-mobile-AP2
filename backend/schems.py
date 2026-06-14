from pydantic import BaseModel
from typing import Optional

class ObjetoBase(BaseModel):
    nome: str
    categoria: str
    status: str
    local: Optional[str] = None
    descricao: Optional[str] = None

class ObjetoCreate(ObjetoBase):
    pass

class ObjetoUpdate(ObjetoBase):
    pass

class ObjetoResponse(ObjetoBase):
    id: int
    recomendacao: Optional[str] = None

    class Config:
        from_attributes = True