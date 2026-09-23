package com.tarinoita.solsweetpotato.integration;

import com.tarinoita.solsweetpotato.SOLSweetPotato;
import net.minecraft.world.entity.player.Player;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.Arrays;
import java.lang.reflect.Modifier;
import java.util.WeakHashMap;

/** Optional integration with the Origins Forge API. */
public final class Origins {
    private static final Map<Player, Boolean> CACHE = new WeakHashMap<>();

    public static void cacheInvalidate(Player player) { CACHE.remove(player); }
    public static void clearCache() { CACHE.clear(); }

    public static boolean hasRestrictedDiet(Player player) {
        return CACHE.computeIfAbsent(player, Origins::readRestrictedDiet);
    }

    private static boolean readRestrictedDiet(Player player) {
        try {
            Class<?> type = Class.forName("io.github.edwinmindcraft.origins.api.capabilities.IOriginContainer");
            Method getter = Arrays.stream(type.getMethods())
                .filter(method -> method.getName().equals("get") && Modifier.isStatic(method.getModifiers())
                    && method.getParameterCount() == 1 && method.getParameterTypes()[0].isAssignableFrom(Player.class))
                .findFirst().orElseThrow(NoSuchMethodException::new);
            Object value = getter.invoke(null, player);
            Object container = value instanceof Optional<?> optional ? optional.orElse(null) : value;
            if (container != null && !type.isInstance(container)) {
                container = container.getClass().getMethod("orElse", Object.class).invoke(container, (Object) null);
            }
            if (container == null) return false;
            Object origins = type.getMethod("getOrigins").invoke(container);
            if (!(origins instanceof Map<?, ?> map)) return false;
            for (Object key : map.values()) {
                String id = String.valueOf(key);
                if (id.contains("origins:vegetarian") || id.contains("origins:carnivore")) return true;
            }
        } catch (ReflectiveOperationException | LinkageError error) {
            SOLSweetPotato.LOGGER.warn("Origins diet integration is unavailable", error);
        }
        return false;
    }
}
