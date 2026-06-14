from fastapi import FastAPI, Depends, HTTPException
from sqlalchemy.orm import Session

import models
import crud
from database import engine, SessionLocal
from schems import ObjetoCreate, ObjetoUpdate, ObjetoResponse

models.Base.metadata.create_all(bind=engine)

app = FastAPI(
    title="API Cadê? - Achados e Perdidos",
    description="API REST para cadastro de objetos perdidos e encontrados.",
    version="1.0.0"
)

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()


@app.get("/")
def home():
    return {
        "mensagem": "API Cadê? funcionando!"
    }


@app.get("/objetos", response_model=list[ObjetoResponse])
def listar(db: Session = Depends(get_db)):
    return crud.listar_objetos(db)


@app.get("/objetos/{objeto_id}", response_model=ObjetoResponse)
def buscar(objeto_id: int, db: Session = Depends(get_db)):
    objeto = crud.buscar_objeto(db, objeto_id)

    if objeto is None:
        raise HTTPException(status_code=404, detail="Objeto não encontrado")

    return objeto


@app.post("/objetos", response_model=ObjetoResponse)
def criar(objeto: ObjetoCreate, db: Session = Depends(get_db)):
    return crud.criar_objeto(db, objeto)


@app.put("/objetos/{objeto_id}", response_model=ObjetoResponse)
def atualizar(objeto_id: int, objeto: ObjetoUpdate, db: Session = Depends(get_db)):
    objeto_atualizado = crud.atualizar_objeto(db, objeto_id, objeto)

    if objeto_atualizado is None:
        raise HTTPException(status_code=404, detail="Objeto não encontrado")

    return objeto_atualizado


@app.delete("/objetos/{objeto_id}")
def deletar(objeto_id: int, db: Session = Depends(get_db)):
    objeto_deletado = crud.deletar_objeto(db, objeto_id)

    if objeto_deletado is None:
        raise HTTPException(status_code=404, detail="Objeto não encontrado")

    return {
        "mensagem": "Objeto deletado com sucesso"
    }


# rodar main e colocar no terminal os links  

# python -m uvicorn main:app --reload   (rodar o servidor)  

# http://127.0.0.1:8000/docs   

# http://127.0.0.1:8000/objetos

# http://127.0.0.1:8000