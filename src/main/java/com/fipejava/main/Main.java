package com.fipejava.main;

import com.fipejava.models.Data;
import com.fipejava.models.Modelos;
import com.fipejava.models.Veiculo;
import com.fipejava.service.ApiConsume;
import com.fipejava.service.ConvertData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private ApiConsume apiConsume = new ApiConsume();
    private ConvertData convertData = new ConvertData();

    private String ADDRESS = "https://parallelum.com.br/fipe/api/v1/";

    public void showMenu() {
        System.out.println("Você deseja fazer a busca por: Carros, Motos, Caminhões ?");
        var response = scanner.nextLine().toLowerCase();
        setADDRESS(ADDRESS + response + "/marcas");
        var json = apiConsume.getData(getADDRESS());
        var marcas = convertData.getDataList(json, Data.class);
        marcas.stream()
                .sorted(Comparator.comparing(Data::codigo))
                .forEach(System.out::println);

        System.out.println("Agora escolha a marca do carro: ");
        response = scanner.nextLine().toLowerCase();
        setADDRESS(ADDRESS + "/" + response + "/modelos");
        json = apiConsume.getData(getADDRESS());
        var modeloLista = convertData.getData(json, Modelos.class);
        System.out.println("\nModelos dessa marca: ");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Data::codigo))
                .forEach(System.out::println);

        System.out.println("\nDigite um trecho do nome do carro a ser buscado");
        var nomeVeiculo = scanner.nextLine();

        List<Data> modelosFiltrados =  modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nModelos filtrados");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("Digite o codigo do modelo para buscar os valores de avaliação");
        var codigoModelo = scanner.nextLine();
        setADDRESS(ADDRESS + "/" + codigoModelo + "/anos");
        json = apiConsume.getData(getADDRESS());
        List<Data> anos = convertData.getDataList(json, Data.class);
        List<Veiculo> veiculos = new ArrayList<>();
        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = getADDRESS() + "/" + anos.get(i).codigo();
            json = apiConsume.getData(enderecoAnos);
            Veiculo veiculo = convertData.getData(json, Veiculo.class);
            veiculos.add(veiculo);
        }
        System.out.println("\nTodos os veiculos filtrados com avaliações por ano: ");
        veiculos.forEach(System.out::println);

    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getADDRESS() {
        return ADDRESS;
    }
}
