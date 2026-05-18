package co.com.polijic.ucation.domain.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.DestinationSetter;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class Mapper {
    private static ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
    }

    private Mapper() {
    }

    public static <D, T> D map(T entidad, Class<D> dto) {
        return modelMapper.map(entidad, dto);
    }

    public static <D, T> List<D> mapAll(Collection<T> listaEntidad, Class<D> dto) {
        return (List) listaEntidad.stream().map((entity) -> {
            return map(entity, dto);
        }).collect(Collectors.toList());
    }

    public static <S, D> D map(S source, D destination) {
        modelMapper.map(source, destination);
        return destination;
    }

    public static void setMatchingStrategy(MatchingStrategy strategy) {
        modelMapper.getConfiguration().setMatchingStrategy(strategy);
    }

    public static <S, D, V> void addSkip(Class<S> sourceType, Class<D> destinationType, DestinationSetter<D, V> data) {
        modelMapper.typeMap(sourceType, destinationType).addMappings((mapper) -> {
            mapper.skip(data);
        });
    }

    public static Map<String, Method> obtenerMapaMetodosDeclarados(Class clase, int cantidadParametros) {
        Map<String, Method> mapaMetodos = new HashMap();
        Arrays.stream(clase.getDeclaredMethods()).forEach((m) -> {
            if (m.getParameterCount() == cantidadParametros || cantidadParametros == -1) {
                mapaMetodos.put(m.getName(), m);
                mapaMetodos.put(m.getName().toUpperCase(), m);
            }

        });
        return mapaMetodos;
    }

    public static <D> List<D> convertirAListaObjeto(List<ArrayList<Object>> datos, Class<D> clase, String[] nombres) {
        return (List) datos.stream().map((x) -> {
            return map((String[]) nombres, (List) x, clase);
        }).collect(Collectors.toList());
    }

    public static <D> List<D> convertirAListaObjeto(List<ArrayList<Object>> datos, Class<D> clase) {
        return convertirAListaObjeto(datos, clase, (String[]) Arrays.stream(clase.getDeclaredFields()).map(Field::getName).toArray((x$0) -> {
            return new String[x$0];
        }));
    }

    public static <D> D map(String[] nombres, List<Object> lista, Class<D> clase) {
        try {
            D objeto = clase.newInstance();
            Map<String, Method> mapaMetodos = obtenerMapaMetodosDeclarados(clase, 1);

            for (int i = 0; i < nombres.length; ++i) {
                ((Method) mapaMetodos.get("set" + StringUtils.capitalize(nombres[i]))).invoke(objeto, lista.get(i));
            }

            return objeto;
        } catch (Throwable var6) {
            return null;
        }
    }

    public static <D> D map(Map<String, String> mapaNombres, Map<String, Object> mapa, Class<D> clase) {
        try {
            D objeto = clase.newInstance();
            Map<String, Method> mapaMetodos = obtenerMapaMetodosDeclarados(clase, 1);
            mapa.forEach((clave, valor) -> {
                Object var10001;
                try {
                    var10001 = mapaNombres.get(clave);
                    ((Method) mapaMetodos.get("set" + StringUtils.capitalize((String) var10001))).invoke(objeto, valor);
                } catch (Exception var8) {
                    try {
                        var10001 = mapaNombres.get(clave);
                        ((Method) mapaMetodos.get("SET" + ((String) var10001).toUpperCase())).invoke(objeto, valor);
                    } catch (Exception var7) {
                    }
                }

            });
            return objeto;
        } catch (Throwable var5) {
            return null;
        }
    }

    public static <D> List<D> convertirListaMapaAListaObjeto(List<Map<String, Object>> datos, Class<D> clase, Map<String, String> mapaNombres) {
        return (List) datos.stream().map((x) -> {
            return map(mapaNombres, x, clase);
        }).collect(Collectors.toList());
    }

    public static <D> List<D> convertirListaMapaAListaObjeto(List<Map<String, Object>> datos, Class<D> clase) {
        return convertirListaMapaAListaObjeto(datos, clase, 0);
    }

    public static <D> List<D> convertirListaMapaAListaObjeto(List<Map<String, Object>> datos, Class<D> clase, int tipoMapeo) {
        Map<String, String> mapaNombres = new HashMap();
        if (tipoMapeo == 0) {
            ArrayList<String> nombres = new ArrayList();
            nombres.addAll(((Map) datos.get(0)).keySet());

            for (int i = 0; i < nombres.size() && i < clase.getDeclaredFields().length; ++i) {
                mapaNombres.put((String) nombres.get(i), clase.getDeclaredFields()[i].getName());
            }
        }

        if (tipoMapeo == 1) {
            ((Map) datos.get(0)).forEach((clave, valor) -> {
                mapaNombres.put(clave.toString(), formatoCampo(clave.toString()));
            });
        }

        return convertirListaMapaAListaObjeto(datos, clase, mapaNombres);
    }

    public static String formatoCampo(String campo) {
        String resultado = (String) Arrays.stream(campo.split("_")).map((x) -> {
            String var10000 = x.substring(0, 1).toUpperCase();
            return var10000 + x.substring(1).toLowerCase();
        }).collect(Collectors.joining(""));
        String var10000 = resultado.substring(0, 1).toLowerCase();
        return var10000 + resultado.substring(1);
    }

    public static <D> D deMapADto(Map<String, Object> mapaDatos, Class<D> clase) {
        if (Objects.nonNull(mapaDatos)) {
            try {
                D objeto = clase.getDeclaredConstructor().newInstance();
                Map<String, Method> mapaMetodos = obtenerMapaMetodosDeclarados(clase);
                mapaDatos.forEach((clave, valor) -> {
                    try {
                        ((Method) mapaMetodos.get("set" + StringUtils.capitalize(clave))).invoke(objeto, valor);
                    } catch (Exception var7) {
                        System.err.println();

                        try {
                            ((Method) mapaMetodos.get("SET" + clave.toUpperCase())).invoke(objeto, valor);
                        } catch (Exception var6) {
                            System.err.println();
                        }
                    }

                });
                return objeto;
            } catch (Throwable var4) {
                return null;
            }
        } else {
            try {
                return clase.getDeclaredConstructor().newInstance();
            } catch (Throwable var5) {
                return null;
            }
        }
    }

    private static Map<String, Method> obtenerMapaMetodosDeclarados(Class clase) {
        Map<String, Method> mapaMetodos = new HashMap();
        Arrays.stream(clase.getDeclaredMethods()).forEach((m) -> {
            if (m.getParameterCount() == 1) {
                mapaMetodos.put(m.getName(), m);
                mapaMetodos.put(m.getName().toUpperCase(), m);
            }

        });
        return mapaMetodos;
    }
}