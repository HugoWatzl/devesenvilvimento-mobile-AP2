from sqlalchemy import Column, Integer, String
from database import Base

class Objeto(Base):
    __tablename__ = "objetos"

    id = Column(Integer, primary_key=True, index=True)
    nome = Column(String, nullable=False)
    categoria = Column(String, nullable=False)
    status = Column(String, nullable=False)
    local = Column(String, nullable=True)
    descricao = Column(String, nullable=True)
    recomendacao = Column(String, nullable=True)