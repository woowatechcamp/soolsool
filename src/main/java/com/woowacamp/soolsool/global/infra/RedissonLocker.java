package com.woowacamp.soolsool.global.infra;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedissonLocker {

    private static final long LOCK_WAIT_TIME = 3L;
    private static final long LOCK_LEASE_TIME = 3L;

    private final RedissonClient redissonClient;

    public RLock getLock(final LockType lockType, final Long id) {
        return redissonClient.getLock(lockType.getKey() + id);
    }

    public List<RLock> getLockAll(final LockType lockType, final List<Long> ids) {
        return ids.stream()
            .map(id -> getLock(lockType, id))
            .collect(Collectors.toList());
    }

    public void tryLock(final RLock rLock) throws InterruptedException {
        rLock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS);
    }

    public void tryLockAll(final List<RLock> rLocks) throws InterruptedException {
        for (RLock rLock : rLocks) {
            tryLock(rLock);
        }
    }

    public void unlock(final RLock rLock) {
        if (rLock.isLocked() && rLock.isHeldByCurrentThread()) {
            rLock.unlock();
        }
    }

    public void unlockAll(final List<RLock> rLocks) {
        for (RLock rLock : rLocks) {
            unlock(rLock);
        }
    }
}
