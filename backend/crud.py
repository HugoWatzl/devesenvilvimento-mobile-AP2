from sqlalchemy.orm import Session
from models import Objeto
from schems import ObjetoCreate, ObjetoUpdate

def gerar_recomendacao(categoria: str, status: str) -> str:
    categoria = categoria.lower()
    status = status.lower()

    if status == "perdi":
        if categoria == "documentos":
            return "Procure a administração e considere bloquear documentos ou cartões importantes."
        elif categoria == "eletronicos":
            return "Verifique a localização do aparelho e informe a central de segurança."
        elif categoria == "roupas":
            return "Confira os locais por onde passou e procure o setor de achados e perdidos."
        else:
            return "Procure a central de achados e perdidos e informe os detalhes do objeto."

    elif status == "encontrei":
        if categoria == "documentos":
            return "Entregue o documento na administração para que o dono seja localizado."
        elif categoria == "eletronicos":
            return "Entregue o eletrônico na central de segurança e evite tentar desbloquear o aparelho."
        elif categoria == "roupas":
            return "Entregue a roupa no setor de achados e perdidos com a informação do local encontrado."
        else:
            return "Entregue o objeto na central de achados e perdidos."

    return "Status inválido. Informe se você perdeu ou encontrou o objeto."


def listar_objetos(db: Session):
    return db.query(Objeto).all()


def buscar_objeto(db: Session, objeto_id: int):
    return db.query(Objeto).filter(Objeto.id == objeto_id).first()


def criar_objeto(db: Session, objeto: ObjetoCreate):
    recomendacao = gerar_recomendacao(objeto.categoria, objeto.status)

    novo_objeto = Objeto(
        nome=objeto.nome,
        categoria=objeto.categoria,
        status=objeto.status,
        local=objeto.local,
        descricao=objeto.descricao,
        recomendacao=recomendacao
    )

    db.add(novo_objeto)
    db.commit()
    db.refresh(novo_objeto)

    return novo_objeto


def atualizar_objeto(db: Session, objeto_id: int, objeto: ObjetoUpdate):
    objeto_existente = buscar_objeto(db, objeto_id)

    if objeto_existente is None:
        return None

    objeto_existente.nome = objeto.nome
    objeto_existente.categoria = objeto.categoria
    objeto_existente.status = objeto.status
    objeto_existente.local = objeto.local
    objeto_existente.descricao = objeto.descricao
    objeto_existente.recomendacao = gerar_recomendacao(objeto.categoria, objeto.status)

    db.commit()
    db.refresh(objeto_existente)

    return objeto_existente


def deletar_objeto(db: Session, objeto_id: int):
    objeto_existente = buscar_objeto(db, objeto_id)

    if objeto_existente is None:
        return None

    db.delete(objeto_existente)
    db.commit()

    return objeto_existente