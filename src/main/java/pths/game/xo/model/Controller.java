package pths.game.xo.model;

public class Controller {
    private int size = 0;
    Model[] models = new Model[100_000];

    public Model createNewModel(ModelChecker checker) {
        var model = new Model(size + 1, checker);
        models[size++] = model;
        return model;
    }

    public Model getModel(int modelId) {
        if (modelId <= 0 || modelId > size) throw new IllegalArgumentException("Модель не найдена");
        return models[modelId - 1];
    }
}
