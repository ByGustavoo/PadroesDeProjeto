package br.com.atlas.factory;

// Parâmetro da Factory: o cliente diz qual tipo quer, sem citar nenhuma classe concreta.
public enum TipoNotificacao {

    EMAIL,
    SMS,
    PUSH
}